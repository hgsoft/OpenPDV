package br.com.openpdv.controlador.permissao;

import br.com.openpdv.controlador.core.CoreService;
import br.com.openpdv.modelo.core.Dados;
import br.com.openpdv.modelo.core.OpenPdvException;
import br.com.openpdv.modelo.core.filtro.*;
import br.com.openpdv.modelo.sistema.SisUsuario;

/**
 * Classe que implementa a chamada no servidor da função de entrar no sistema,
 * acessando os dados para autenticar o usuário junto ao servidor.
 *
 * @param <E> o tipo de dados.
 * <p/>
 * @author Pedro H. Lira
 */
public class PermissaoService extends CoreService<SisUsuario> {

    /**
     * Construtor padrao.
     */
    public PermissaoService() {
        super(PermissaoService.class);
    }

    /**
     * Metodo que valida o usuario no sistema.
     *
     * @param usuario login do usuario.
     * @param senha   senha do usuario.
     * <p/>
     * @return Um objeto SisUsuario caso consiga logar.
     * <p/>
     * @throws OpenPdvException dispara uma excecao caso nao consiga.
     */
    public SisUsuario validar(String usuario, String senha) throws OpenPdvException {
        // cria os dois filtros contendo os valores de login
        FiltroTexto ft1 = new FiltroTexto("sisUsuarioLogin", ECompara.IGUAL, usuario);
        FiltroTexto ft2 = new FiltroTexto("sisUsuarioSenha", ECompara.IGUAL, senha);
        FiltroBinario fb = new FiltroBinario("sisUsuarioAtivo", ECompara.IGUAL, true);
        GrupoFiltro gf = new GrupoFiltro(EJuncao.E, new IFiltro[]{ft1, ft2, fb});

        try {
            return selecionar(new SisUsuario(), gf);
        } catch (Exception ex) {
            log.error("Erro ao validar usuario.", ex);
            throw new OpenPdvException(ex.getMessage());
        }
    }
}