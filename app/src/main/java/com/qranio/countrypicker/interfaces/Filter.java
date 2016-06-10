package com.qranio.countrypicker.interfaces;


public interface Filter<T> {

    /**
     * Verifica se o objeto passa pelo filtro.
     *
     * @param candidate
     *            Objeto candidato.
     * @return <b>true</b> - o objeto candidato corresponde ao filtro. <br>
     *         <b>false</b> - o objeto candidato não corresponde ao filtro.
     */
    public boolean match(T candidate);
}