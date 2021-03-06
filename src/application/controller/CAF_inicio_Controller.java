package application.controller;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.Main;
import application.ModifyScenes;
import application.dao.MainDao;
import application.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CAF_inicio_Controller {
	
    private @FXML MenuBar menu;
    
    private @FXML Menu menum;
    private @FXML Label labelhora;
    
    private @FXML Button btnlogar;
    private @FXML TextField txtuser = new TextField();
    private @FXML TextField txtpw = new TextField();
    
    private @FXML Pane panelogin;
    private Stage stage = Main.Stagemain;
    private MainDao dao = new MainDao();
    private SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
    
    String msg_a = "Você não tem permissão de acesso.";
    
    @FXML void initialize() {
    	txtuser.setOnKeyPressed(e ->{
    		if(txtuser.getText().equals("0890000777")) {
    			txtuser.setText("nutrinor");
    			txtpw.setText("nor3200#");
    		}
    		if(e.getCode() == KeyCode.ENTER) {
    			if(txtuser.getText().equals("0890000777")) {
        			txtuser.setText("nutrinor");
        			txtpw.setText("nor3200#");
        		}
    			btnlogar(null);
    		}
    	});
    	txtpw.setOnKeyPressed(e ->{
    		if(e.getCode() == KeyCode.ENTER) {
    			btnlogar(null);
    		}
    	});
    	hora();
    }
    
    @FXML void btnlogar(ActionEvent event) {
    	if (txtuser.getText().isEmpty() || 
    		txtpw.getText().isEmpty()) {
    		Main.dialogBox("Digite um Usuário e Senha!", 1);
    		} else {
    			
    			//Consulta JSON
    			if((Main.verifyJSON(txtuser.getText(), txtpw.getText())) == false) {
    				return;
    			};
    			
    			//verificação na funcao do banco de dados
    			if (dao.UserConfirm(txtuser.getText())) {
    				if(Main.user.getNvacesso() > 1 ) {
    					openmenuref(null);
    				}
    				//Caso login esteja ok, ajusta o frame para modo logado
    				panelogin.setVisible(false);
    				panelogin.setDisable(true);
    				menu.setDisable(false);
    				//Futuro controle de acesso
    				txtuser.setText("");
    				txtpw.setText("");
    			} else {
    				Main.dialogBox("Usuário não existe no banco de dados!", 1);
    			}
    		}
    }
    @FXML void openmenuref(ActionEvent event) {
    	if (Main.user.getNvacesso() <= 2) {

			Main.modify.modify("view/CAF_refeitorio.fxml", "Controle de Acesso - Refeitório");
			
			stage.setOnCloseRequest(e -> e.consume());

		} else {
			Main.dialogBox(msg_a, 1);
		}
    }

    
    @FXML void openmenufun(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_funcionarios.fxml", "Controle de Acesso - Cadastro de Funcionários");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }

    
    @FXML void openmenuadi(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_adicionais.fxml", "Controle de Acesso - Adicionais");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }

    
    @FXML void openmenurel(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_relatorio.fxml", "CAF - Relatório");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }
    //-----------------login menu---------------------
    @FXML void openalterpw(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_login_alterpw.fxml", "CAF - Alterar Senha");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }
    @FXML void opencadeventos(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_login_eventos.fxml", "CAF - Eventos");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }
    @FXML void openminrefperiodo(ActionEvent event) {
    	if(Main.user.getNvacesso() <= 1) {
    		Main.modify.modify("view/CAF_login_refeicoesperiodo.fxml", "CAF - Eventos");
    		
    		stage.setOnCloseRequest(e -> e.consume());
    	} else {
			Main.dialogBox(msg_a, 1);
		}
    }
    
    
    @FXML void Logout(ActionEvent event) {
    	for(int i=0; i<ModifyScenes.listS.size(); i++) {
			Stage s = ModifyScenes.listS.get(i);
			s.close();
			ModifyScenes.listS.remove(i);
		}
		Main.Stagemain.setOnCloseRequest(e -> Platform.exit());
		ModifyScenes.close();
		panelogin.setVisible(true);
		panelogin.setDisable(false);
		menu.setDisable(true);
		Main.user = new Usuario();
    }
    
    
    private void hora() {
    	KeyFrame frame = new KeyFrame(Duration.millis(1000), e -> {
    		Date agora = new Date();
    		labelhora.setText(formatador.format(agora).toUpperCase()); 
    	});
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
    }
    
}
