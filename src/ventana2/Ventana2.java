package ventana2;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ventana2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textNombre;
    private JTextField textNif;
    private JTextField textTarjetaCredito;

    private final String PATH = "C:\\Users\\Usuario\\Desktop\\ventanaCliente2\\src\\ventana2";
    private final String FILE_NAME = "clients.txt";
    private final String PATH_TO_FILE = PATH + "\\" + FILE_NAME;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana2 frame = new Ventana2();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Ventana2() {
        setTitle("Introducción de datos del cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 895, 605);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Cliente");
        lblNewLabel.setBounds(38, 35, 51, 26);
        contentPane.add(lblNewLabel);

        JLabel lblNif = new JLabel("NIF");
        lblNif.setBounds(38, 72, 51, 26);
        contentPane.add(lblNif);

        JLabel lblTarjetaDeCredito = new JLabel("TARJETA DE CRÉDITO");
        lblTarjetaDeCredito.setBounds(38, 109, 120, 26);
        contentPane.add(lblTarjetaDeCredito);

        textNombre = new JTextField();
        textNombre.setBounds(180, 38, 178, 20);
        contentPane.add(textNombre);
        textNombre.setColumns(10);

        textNif = new JTextField();
        textNif.setColumns(10);
        textNif.setBounds(180, 75, 178, 20);
        contentPane.add(textNif);

        textTarjetaCredito = new JTextField();
        textTarjetaCredito.setColumns(10);
        textTarjetaCredito.setBounds(180, 112, 178, 20);
        contentPane.add(textTarjetaCredito);

        JButton darAltaBoton = new JButton("Dar de alta");
        darAltaBoton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                boolean dataValidateOk = validateClientData(textNombre.getText(), textNif.getText(), textTarjetaCredito.getText());
                boolean nifValidateOk = validateNifAlgorithm(textNif.getText());

                if (dataValidateOk && nifValidateOk) {
                    try {
                        FileWriter fw = new FileWriter(PATH_TO_FILE, true);
                        fw.write("El cliente ha sido registrado:\n" + "Nombre: " + textNombre.getText() + " | "
                                + "NIF: " + textNif.getText() + " | " + "Tarjeta Crédito: "
                                + textTarjetaCredito.getText());

                        fw.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }

                    verMensaje("Nombre: " + textNombre.getText() + "\n" + "NIF: " + textNif.getText() + "\n"
                            + "Tarjeta Crédito: " + textTarjetaCredito.getText());

                    verMensaje("Registro guardado");
                    textNombre.setText("");
                    textNif.setText("");
                    textTarjetaCredito.setText("");
                } else {
                    textNombre.setText("");
                    textNif.setText("");
                    textTarjetaCredito.setText("");
                }
            }
        });
        darAltaBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        darAltaBoton.setBounds(180, 158, 178, 23);
        contentPane.add(darAltaBoton);
    }

    private void verMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private boolean validateClientData(String name, String nif, String creditCardNumber) {

        // Quito espacios y guiones a las cadenas original de NIF y tarjeta de Créito
        creditCardNumber = creditCardNumber.replaceAll("\\s", "");
        creditCardNumber = creditCardNumber.replaceAll("-", "");
        nif = nif.replaceAll("\\s", "");
        nif = nif.replaceAll("-", "");

        // Creo las Regex para los distintos datos
        String nameRegex = "^[\\p{L}]+(?:[\\p{Zs}][\\p{L}]+)*$"
                ;
        String nifRegex = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]$|^[XYZxyz][0-9]{7}$";
        String creditCardNumberRegex = "^(\\d\\s*){16}$";


        // Creo un objeto Pattern compilando la expresión regular
        Pattern namePattern = Pattern.compile(nameRegex);
        Pattern nifPattern = Pattern.compile(nifRegex);
        Pattern creditCardNumberPattern = Pattern.compile(creditCardNumberRegex);

        // Crear un objeto Matcher y realizar la validación
        Matcher nameMatcher = namePattern.matcher(name);
        Matcher nifMatcher = nifPattern.matcher(nif);
        Matcher creditCardNumberMatcher = creditCardNumberPattern.matcher(creditCardNumber);

        // Verifico si la cadena cumple con la expresión regular
        boolean checker = true;
        String message;
        if (!nameMatcher.matches()) {
            message = "Ha introducido algún carácter no válido en su nombre, por favor, introdúzcalo de nuevo";
            JOptionPane.showMessageDialog(null, message);
            System.out.println(message);
            checker = false;
        }

        if (!nifMatcher.matches()) {
            message = "Su DNI no contiene un formato válido, por favor, inténtelo de nuevo";
            JOptionPane.showMessageDialog(null, message);
            System.out.println(message);
            checker = false;
        }

        if (!creditCardNumberMatcher.matches()) {
            message = "El número de su tarjeta de crédito no es correcto, por favor, vuelva a introducirlo";
            JOptionPane.showMessageDialog(null, message);
            System.out.println(message);
            checker = false;
        }

        return checker;
    }


    private boolean validateNifAlgorithm(String nif) {
        String[] lettersTable = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        if (nif.charAt(0) == 'X') {
            char[] nifCharArray = nif.toCharArray();
            nifCharArray[0] = '0';
            String modifiedNifString = new String(nifCharArray);
        } else if (nif.charAt(0) == 'Y') {
            char[] nifCharArray = nif.toCharArray();
            nifCharArray[0] = '1';
            String modifiedNifString = new String(nifCharArray);
        } else if (nif.charAt(0) == 'Z') {
            char[] nifCharArray = nif.toCharArray();
            nifCharArray[0] = '2';
            String modifiedNifString = new String(nifCharArray);
        }

        String letter = nif.substring(8);
        String upperLetter = letter.toUpperCase();
        Integer number = Integer.parseInt(nif.substring(0, 8));
        int rest = number % 23;

        if (lettersTable[rest].equals(upperLetter)) {
            return true;
        } else {
            return false;
        }
    }

    
}
