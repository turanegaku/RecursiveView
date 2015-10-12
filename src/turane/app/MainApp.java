package turane.app;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {
	@FXML
	private ListView<ImageView> imgs;
	@FXML
	private TextField folder;
	@FXML
	private TextField directory;
	@FXML
	private Button prev, next;

	private ListIterator<Path> files = null;
	private Path current = null;

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("main.fxml"));
		stage.setScene(new Scene(root));

		stage.show();
	}
	
	@FXML
	public void folder(ActionEvent e){
		try {
			Files.move(current, current.resolveSibling(folder.getText()));
			nxt(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	public void dir(ActionEvent e){
		try {
			files = Files.list(Paths.get(directory.getText()))
					.filter(p->Files.isDirectory(p))
					.collect(Collectors.toList())
					.listIterator();
			next.requestFocus();
			nxt(e);
		} catch (IOException e1) {
			files = null;
			e1.printStackTrace();
		}
	}

	private void setImages(Path f){
		folder.setText(f.getFileName().toString());
		try {
			Files.find(f, Integer.MAX_VALUE, (path, attr) -> path.getFileName().toString().matches(".*\\.(jpg)*(png)*"))
			.filter(path->!Files.isDirectory(path))
			.forEach(path->{
				try {
					InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
					ImageView iv = new ImageView(new Image(is));
					if(iv.getImage().getWidth() > imgs.getWidth()){
						iv.fitWidthProperty().bind(imgs.widthProperty().subtract(20));
						iv.setPreserveRatio(true);
					}
					imgs.getItems().add(iv);
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void prv(ActionEvent e){
		imgs.getItems().clear();
		if (files == null) {
			folder.setText("Fail to load root Directory");
			return;
		} else if (!files.hasPrevious()){
			folder.setText("Reach to final items");
			return;
		}
		Path p = files.previous();
		if (p.equals(current)) {
			prv(e);
			return;
		}
		current = p;
		setImages(current);
		imgs.scrollTo(0);
	}
	@FXML
	private void nxt(ActionEvent e){
		imgs.getItems().clear();
		if (files == null) {
			folder.setText("Fail to load root Directory");
			return;
		} else if (!files.hasNext()){
			folder.setText("Reach to final items");
			return;
		}
		Path p = files.next();
		if (p.equals(current)) {
			nxt(e);
			return;
		}
		current = p;
		setImages(current);
		imgs.scrollTo(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
