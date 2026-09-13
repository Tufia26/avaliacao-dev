package br.com.soc.sistema.infra;

public enum DisponibilidadeEnum {
    MANHA("1", "Manhã"),
    TARDE("2", "Tarde"),
    AMBOS("3", "Ambos");

    private final String codigo;
    private final String descricao;

    DisponibilidadeEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static DisponibilidadeEnum buscarPorCodigo(String codigo) {
        for (DisponibilidadeEnum disp : values()) {
            if(disp.getCodigo().equals(codigo))
                return disp;
        }
        return null;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}