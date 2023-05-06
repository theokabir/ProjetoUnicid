package utils;

import javax.swing.*;
import java.awt.*;

public class Messages {

    /**
     * Fuinção estática para reduzir a codificação de mensagens de erro
     * @param message mensagem a ser exibida em uma função {@link JOptionPane#showMessageDialog(Component, Object, String, int) showMessageDialog}
     */
    public static void error(String message){
        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Função estática para falicitar a utilização das mensagens com {@link JOptionPane#showMessageDialog(Component, Object)}
     * @param message mensagem a ser mostrada para o usuário
     */
    public static void popup(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Função estática feita para facilitar a utilização do método {@link JOptionPane#showConfirmDialog(Component, Object, String, int)}
     * @param message mensagem a ser mostrada ao usuário
     * @return um boolean que mostra se o usuário confirmou ou não o dialogo de confirmação
     */
    public static boolean confirm(String message){
        int dialogConfirm = JOptionPane.showConfirmDialog(
                null,
                message,
                "confirmar",
                JOptionPane.YES_NO_OPTION);

        return dialogConfirm == 0;
    }

}
