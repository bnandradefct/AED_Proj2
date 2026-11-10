package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * SepChain Hash Table Iterator
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.1
 * @param <K> Generic Key
 * @param <V> Generic Value
 *
 * Space complexity: O(1)
 */
class SepChainHTIterator<K, V> implements Iterator<Map.Entry<K, V>> {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tabela de dispersão (array de baldes/conjuntos).
     */
    private final Set<Map.Entry<K, V>>[] table;

    /**
     * Índice do balde atual a ser percorrido.
     */
    private int currentBucket;

    /**
     * Iterador do balde atual. É null se a tabela estiver vazia ou terminada.
     */
    private Iterator<Map.Entry<K, V>> bucketIterator;

    /**
     * Construtor do iterador.
     * 
     * Time complexity:
     * - Best case: O(1) (o primeiro balde tem elementos)
     * - Worst case: O(m) onde m é a capacidade da tabela
     * 
     * @param table Array de conjuntos com as entradas da hash table
     */
    public SepChainHTIterator(Set<Map.Entry<K, V>>[] table) {
        this.table = table;
        this.rewind();
    }

    /**
     * Procura o próximo balde não nulo e com elementos a partir de currentBucket.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(m) onde m é o comprimento da tabela
     */
    private void findNextBucket() {
        while (currentBucket < table.length) {
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                bucketIterator = table[currentBucket].iterator();
                return;
            }
            currentBucket++;
        }
        bucketIterator = null;
    }

    /**
     * Verifica se ainda existem elementos por visitar na tabela.
     * 
     * Time complexity: O(1)
     */
    @Override
    public boolean hasNext() {
        if (bucketIterator == null) {
            return false;
        }
        if (bucketIterator.hasNext()){
            return true;
        }
            currentBucket++;
            findNextBucket();
        return bucketIterator != null && bucketIterator.hasNext();
    }

    /**
     * Devolve a próxima entrada (chave-valor) e avança o iterador.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(m) se tiver de saltar baldes vazios até ao próximo elemento
     * 
     * @return Próxima Entry<K, V>
     * @throws NoSuchElementException se não existirem mais elementos
     */
    @Override
    public Map.Entry<K, V> next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return bucketIterator.next();
    }

    /**
     * Reinicia o iterador para a primeira posição com elementos.
     * 
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(m)
     */
    @Override
    public void rewind() {
        currentBucket = 0;
        bucketIterator = null;
        findNextBucket();
    }
}
