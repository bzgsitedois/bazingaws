package com.bazinga.exception;

import java.util.List;

public class ErrorResponse {
    private int status;
    private String mensagem;
    private List<String> erros;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.mensagem = message;
    }

    public ErrorResponse(int status, String message, List<String> erros) {
        this.status = status;
        this.mensagem = message;
        this.erros = erros;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMessage(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
