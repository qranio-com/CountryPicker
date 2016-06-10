package com.qranio.countrypicker.util;

import com.qranio.countrypicker.interfaces.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Esta classe utilitária realiza manipulações em uma coleção de dados
 *
 * @author Rafael C. Almeida - | - <b> rafaelcarvalho.dev@gmail.com</b>
 *
 */
public class JCollections {

    /**
     * Construtor da classe, não permite criar instâncias.
     */
    private JCollections() {
    }

    /**
     * Retorna o primeiro item da lista que atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @return primeiro item que atender ao filtro, em caso de nenhum item
     *         atender ao filtro retorna null
     */
    public static <T> T findFirstItemFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter) {

        for (T object : listItensCandidates) {

            if (filter.match(object)) {

                return object;
            }
        }

        return null;
    }

    /**
     * Retorna o primeiro item da lista que não atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @return primeiro item que atender ao filtro, em caso de nenhum item
     *         atender ao filtro retorna null
     */
    public static <T> T findFirstItemNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter) {

        for (T object : listItensCandidates) {

            if (!filter.match(object)) {

                return object;
            }
        }

        return null;
    }

    /**
     * Retorna todos os itens da lista que atenderem ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @return lista de itens filtrados
     */
    public static <T> List<T> findAllItensFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter) {

        List<T> listFiltered = new ArrayList<T>(0);

        addAllItensFilter(listItensCandidates, filter, listFiltered);

        return listFiltered;
    }

    /**
     * Retorna todos os itens da lista que não atenderem ao filtro, que forem
     * diferentes do filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @return lista de itens filtrados
     */
    public static <T> List<T> findAllItensNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter) {

        List<T> listFiltered = new ArrayList<T>(0);

        addAllItensNotFilter(listItensCandidates, filter, listFiltered);

        return listFiltered;
    }

    /**
     * Remove da lista o primeiro item que atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeFirstItemFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (filter.match(iterator.next())) {

                iterator.remove();
                return;
            }
        }
    }

    /**
     * Remove da lista o primeiro item que não atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeFirstItemNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (!filter.match(iterator.next())) {

                iterator.remove();
                return;
            }
        }
    }

    /**
     * Remove da lista todos os itens que não atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeAllItensNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (!filter.match(iterator.next())) {

                iterator.remove();
            }
        }
    }

    /**
     * Remove da lista o último item que atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeLastItemFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        Collections.reverse((List<?>) listItensCandidates);

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (filter.match(iterator.next())) {

                iterator.remove();
                Collections.reverse((List<?>) listItensCandidates);
                return;
            }
        }
    }

    /**
     * Remove da lista o último item que não atender ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeLastItemNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        Collections.reverse((List<?>) listItensCandidates);

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (!filter.match(iterator.next())) {

                iterator.remove();
                Collections.reverse((List<?>) listItensCandidates);
                return;
            }
        }
    }

    /**
     * Adiciona na lista de destino todos os itens que atendam ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @param listItensDest
     *            lista de destino
     */
    public static <T> void addAllItensFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter,
            Collection<T> listItensDest) {

        for (T object : listItensCandidates) {

            if (filter.match(object)) {

                listItensDest.add(object);
            }
        }
    }

    /**
     * Adiciona na lista de destino todos os itens que não atenderem ao filtro,
     * que forem diferentes do filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @param listItensDest
     *            lista de destino
     */
    public static <T> void addAllItensNotFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter,
            Collection<T> listItensDest) {

        for (T object : listItensCandidates) {

            if (!filter.match(object)) {

                listItensDest.add(object);
            }
        }
    }

    /**
     * Remove da lista todos os itens que atenderem ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void removeAllItensFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (filter.match(iterator.next())) {

                iterator.remove();
            }
        }
    }

    /**
     * Mantém na lista todos os itens que atenderem ao filtro
     *
     * @param listItensCandidates
     *            lista de itens origem
     * @param filter
     *            filtro a ser utilizado
     * @throws UnsupportedOperationException
     */
    public static <T> void retainAllItensFilter(
            Collection<? extends T> listItensCandidates, Filter<T> filter)
            throws UnsupportedOperationException {

        for (Iterator<? extends T> iterator = listItensCandidates.iterator(); iterator
                .hasNext();) {

            if (!filter.match(iterator.next())) {

                iterator.remove();
            }
        }
    }
}