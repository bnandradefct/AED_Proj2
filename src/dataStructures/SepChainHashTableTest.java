package dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SepChainHashTableTest {

    private SepChainHashTable<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new SepChainHashTable<>();
    }

    private <K, V> void assertMapInvariants(SepChainHashTable<K, V> target) {
        assertEquals(target.size() == 0, target.isEmpty());

        int count = 0;
        Iterator<Map.Entry<K, V>> it = target.iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            assertEquals(entry.value(), target.get(entry.key()));
            count++;
        }
        assertEquals(target.size(), count);
    }

    private record CollidingKey(String id, int customHash) implements Serializable {
        @Override
        public int hashCode() {
            return customHash;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof CollidingKey other) && id.equals(other.id);
        }
    }

    @Test
    void testEmptyMap() {
        assertMapInvariants(map);
        assertNull(map.get("missing"));
        assertNull(map.get(null));
        assertNull(map.remove("missing"));
        assertNull(map.remove(null));
        assertFalse(map.iterator().hasNext());
    }

    @Test
    void testPutUpdateGetAndRemove() {
        assertNull(map.put("j1", 100));
        assertNull(map.put("j2", 200));
        assertNull(map.put("j3", 300));
        assertMapInvariants(map);

        // Atualizar chave existente deve devolver valor antigo e manter size()
        assertEquals(200, map.put("j2", 250));
        assertEquals(3, map.size());
        assertEquals(250, map.get("j2"));
        assertMapInvariants(map);

        // Remover chave existente e voltar a tentar remover
        assertEquals(100, map.remove("j1"));
        assertNull(map.get("j1"));
        assertNull(map.remove("j1"));
        assertEquals(2, map.size());
        assertMapInvariants(map);
    }

    @Test
    void testCollisionsAndNegativeHashCodes() {
        SepChainHashTable<CollidingKey, String> customMap = new SepChainHashTable<>(11);

        // Chaves com colisões forçadas e hashCode == Integer.MIN_VALUE
        CollidingKey k1 = new CollidingKey("A", Integer.MIN_VALUE);
        CollidingKey k2 = new CollidingKey("B", Integer.MIN_VALUE);
        CollidingKey k3 = new CollidingKey("C", -42);
        CollidingKey k4 = new CollidingKey("D", -42);

        assertNull(customMap.put(k1, "ValA"));
        assertNull(customMap.put(k2, "ValB"));
        assertNull(customMap.put(k3, "ValC"));
        assertNull(customMap.put(k4, "ValD"));
        assertEquals(4, customMap.size());

        assertEquals("ValA", customMap.get(k1));
        assertEquals("ValB", customMap.get(k2));
        assertEquals("ValC", customMap.get(k3));
        assertEquals("ValD", customMap.get(k4));

        assertEquals("ValB", customMap.remove(k2));
        assertNull(customMap.get(k2));
        assertEquals("ValA", customMap.get(k1));
        assertEquals(3, customMap.size());
    }

    @Test
    void testRehashMaintainsAllEntries() {
        int totalEntries = 200; // Ultrapassa DEFAULT_CAPACITY (50) e força múltiplos rehashes
        for (int i = 0; i < totalEntries; i++) {
            assertNull(map.put("journey_" + i, i * 10));
        }
        assertMapInvariants(map);

        for (int i = 0; i < totalEntries; i++) {
            assertEquals(i * 10, map.get("journey_" + i));
        }

        for (int i = 0; i < totalEntries; i += 2) {
            assertEquals(i * 10, map.remove("journey_" + i));
        }
        assertEquals(totalEntries / 2, map.size());
        assertMapInvariants(map);
    }

    @Test
    void testKeysAndValuesIterators() {
        map.put("one", 1);
        map.put("two", 2);

        int keyCount = 0;
        Iterator<String> keysIt = map.keys();
        while (keysIt.hasNext()) {
            assertNotNull(map.get(keysIt.next()));
            keyCount++;
        }
        assertEquals(2, keyCount);

        int sum = 0;
        Iterator<Integer> valsIt = map.values();
        while (valsIt.hasNext()) {
            sum += valsIt.next();
        }
        assertEquals(3, sum);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSerialization() throws IOException, ClassNotFoundException {
        for (int i = 0; i < 80; i++) {
            map.put("key-" + i, i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(map);
        }

        SepChainHashTable<String, Integer> restored;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            restored = (SepChainHashTable<String, Integer>) ois.readObject();
        }

        assertMapInvariants(restored);
        assertEquals(80, restored.size());
        for (int i = 0; i < 80; i++) {
            assertEquals(i, restored.get("key-" + i));
        }
    }
}
