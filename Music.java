import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Music {

	public static void main(String[] args) {
		// TODO 
		new VentanaGrafica();		
	}
}

class VentanaGrafica extends JFrame{
	

	//Variables que necesita la interfaz
	JButton boton = new  JButton("Buscar Archivos en la carpeta");
	JButton boton1 = new JButton("Borrar");
	//Virtual box para ejecutar toda las instruciones de la interfaz grafica
	Box grupo = Box.createVerticalBox();
	
	JPanel panel = new JPanel();
	JPanel superior = new JPanel();
	
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	JLabel label3 = new JLabel();
	JLabel label4 = new JLabel();
	JLabel label5 = new JLabel();
	JLabel label6 = new JLabel();
	JLabel label7 = new JLabel();
	JLabel label8 = new JLabel();
	JLabel cancion = new JLabel();
	JLabel peso = new JLabel();
	
	Tag tag=null;
	File archivo;
	AudioFile file;
	FieldKey[]  parametro = {FieldKey.ALBUM,  FieldKey.ARTIST, FieldKey.YEAR, FieldKey.GENRE, 
			FieldKey.TITLE, FieldKey.COMMENT, FieldKey.COVER_ART, FieldKey.TRACK} ;            	        
	//Esta es la ruta por defecto en mi computadora.. entonces varia por cada computadora
	File ruta= new File("C:\\Users\\dell\\Documents\\Música2.0");
	//Ventana grafica que da funcion a los botones y a los label que muestra la informacion
	public VentanaGrafica() {

		// Constructor
		setTitle("Editor de Archivos Mp3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(100, 100, 700, 330));
		superior.add(boton);
		superior.add(boton1);
		grupo.add(label1);
		grupo.add(label2);
		grupo.add(label3);
		grupo.add(label4);
		grupo.add(label5);
		grupo.add(label6);
		grupo.add(label7);
		grupo.add(label8);
		grupo.add(cancion);
		grupo.add(peso);
		//Bordeado del panel principal que mostrara la informacion y dara los datos
		panel.add(grupo);
		add(panel,BorderLayout.CENTER);
		panel.setBackground(new Color(22, 184, 11));          

		superior.setBackground(Color.YELLOW);
		add(superior, BorderLayout.NORTH);
		new Botones();
		setVisible(true);
		
	}
        // clase que activara los botones con la logica de programacion
class Botones{
	
	public Botones() {
		// TODO Auto-generated constructor stub
		
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label1, "Album: ", 0);
			}
		});
		
		label2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label2, "Artista: ", 1);
			}
		});
		
		label3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label3, "Fecha: ", 2);
			}
		});
		
		label4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label4, "Genero: ", 3);
			}
		});
		 
		label5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label5, "Titulo: ", 4);
			}
		});
		
		label6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label6, "Comentario: ", 5);
			}
		});
		
		label8.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				edicionEtiqueta(label8, "Pista: ", 7);
			}
		});
	
		boton.addActionListener(new ActionListener() {					
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				archivo=null;
				JFileChooser destino = new JFileChooser(ruta);
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("MP3", "mp3");
				destino.setFileFilter(filtro);
				destino.showOpenDialog(null);
				
				archivo = destino.getSelectedFile();
				try{
				ruta = new File(archivo.getParent());
				System.out.println(ruta);
				}
				catch (NullPointerException e8) {JOptionPane.showMessageDialog(null, "Seleccionar un archivo");
				}
				try{
					
					if (archivo!=null & archivo.exists()){
						new ObtenerEtiquetas(archivo);
					}
				}
				catch (NullPointerException e2) {System.out.println("Error de Existencia..");
				JOptionPane.showMessageDialog(null, "Archivo no editable");
				archivo=null;
				ResetLabels(label1);ResetLabels(label2);ResetLabels(label3);ResetLabels(label4);ResetLabels(label5);
				ResetLabels(label6);ResetLabels(label7);ResetLabels(label8);ResetLabels(cancion);ResetLabels(peso);
				}
				
		}});	
		
		boton1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try{
					if (archivo!=null & archivo.exists()){
						
						tag.deleteField(FieldKey.ALBUM);
						tag.deleteField(FieldKey.ARTIST);
						tag.deleteField(FieldKey.YEAR);
						tag.deleteField(FieldKey.GENRE);
						tag.deleteField(FieldKey.TITLE);
						tag.deleteField(FieldKey.COMMENT);

						tag.deleteField(FieldKey.TRACK);
						System.out.println("reset");
						try {
							AudioFileIO.write(file);
						} catch (CannotWriteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();}	
						}				
					new ObtenerEtiquetas(archivo);
					
				}
				catch (Exception e3) {System.out.println("Error" );}
				
			}
			});
		
	}
	public void ResetLabels(JLabel label){
		label.setText("");
	}
	
	public void edicionEtiqueta(JLabel label,String texto, int x){
		String data =JOptionPane.showInputDialog("Ingrese de nuevo "+texto);
		
		if (data!=null){
			try {
				tag.setField(parametro[x], data);
				AudioFileIO.write(file);
			} 
			catch (CannotWriteException | KeyNotFoundException | FieldDataInvalidException e1) {
				e1.printStackTrace();}
			label.setText(texto+data);
			}
		}
		
	
}

class ObtenerEtiquetas{
	
	public void labels(int x,JLabel label,String texto){
		
		label.setText(texto + tag.getFirst(parametro[x]));
		if (x!=6){
			label.setForeground(Color.BLUE);
		}
		}
	
	public ObtenerEtiquetas(File archivo) {
		// TODO Auto-generated constructor stub

		file = new AudioFile(); 
		
		 try {
			
			 file = AudioFileIO.read(archivo);			 
			 tag = file.getTag();
			 System.out.println(tag);
			 JLabel label= new JLabel();
			 
			 String texto="";
			 System.out.println(tag.hasCommonFields());
			 
			 for (short x=0;x<parametro.length;x++){
				 if (x==0){label = label1;texto="Album: ";}
				 if (x==1){label = label2;texto="Artista: ";}
				 if (x==2){label = label3;texto="Fecha: ";}
				 if (x==3){label = label4;texto="Genero: ";}
				 if (x==4){label = label5;texto="Titulo: ";}
				 if (x==5){label = label6;texto="Comentario: ";}
				 if (x==6){label = label7;texto="COVER_ART: ";}
				 if (x==7){label = label8;texto="Pista: ";}
				 labels(x, label,texto);			
			}	
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {e.printStackTrace();}
		 cancion.setText("Cancion :"+file.getFile());
		 peso.setText("Tamano: "+archivo.length()+" bytes...");
		 
		 }
	}
}