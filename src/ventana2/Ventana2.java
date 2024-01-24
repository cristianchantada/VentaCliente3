package ventana2;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ventana2 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textNombre;
    private JTextField textNif;
    private JTextField textTarjetaCredito;

    private final String PATH = "C:\\Users\\Profesor\\Desktop\\ventanaCliente2-main\\src\\ventana2";
    private final String FILE_NAME = "clients.txt";
    private final String PATH_TO_FILE = PATH + "\\" + FILE_NAME;
    private JTextField textBankAccount;

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

        JLabel lblNewLabel = new JLabel("CLIENTE");
        lblNewLabel.setBounds(38, 35, 51, 26);
        contentPane.add(lblNewLabel);

        JLabel lblNif = new JLabel("NIF");
        lblNif.setBounds(38, 72, 51, 26);
        contentPane.add(lblNif);

        JLabel lblTarjetaDeCredito = new JLabel("TARJETA DE CRÉDITO");
        lblTarjetaDeCredito.setBounds(38, 109, 141, 26);
        contentPane.add(lblTarjetaDeCredito);

        textNombre = new JTextField();
        textNombre.setBounds(212, 40, 178, 20);
        contentPane.add(textNombre);
        textNombre.setColumns(10);

        textNif = new JTextField();
        textNif.setColumns(10);
        textNif.setBounds(212, 77, 178, 20);
        contentPane.add(textNif);

        textTarjetaCredito = new JTextField();
        textTarjetaCredito.setColumns(10);
        textTarjetaCredito.setBounds(212, 114, 178, 20);
        contentPane.add(textTarjetaCredito);

        JButton darAltaBoton = new JButton("Dar de alta");
        darAltaBoton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	// Quito espacios y guiones a las cadenas original de NIF, tarjeta de Crédito y cuenta bancaria.
            	textNif.setText(textNif.getText().replaceAll("\\s", "").replaceAll("-", ""));
            	textTarjetaCredito.setText(textTarjetaCredito.getText().replaceAll("\\s", "").replaceAll("-", ""));
            	textBankAccount.setText(textBankAccount.getText().replaceAll("\\s", "").replaceAll("-", ""));
            	
                boolean dataValidateOk = validateClientData(textNombre.getText(), textNif.getText(), textTarjetaCredito.getText(), textBankAccount.getText());
                boolean nifValidateOk = false;
                boolean bankAccountOk = false;
                
                // Código lectura fichero para comprobar el número de tarjetas de crédito del cliente
                
                File fileReaderDb = new File(PATH_TO_FILE);
                String nameUpperFormat = textNombre.getText().toUpperCase().replaceAll("\\s", ""); 
                
                try {
                	Scanner sc = new Scanner(fileReaderDb);
                	while(sc.hasNextLine()) {
                		String line = sc.nextLine().replaceAll("\\s", "");
                		String[] clientData = line.split("|");
                		
                		String clientName = clientData[0].toUpperCase();
                		if(clientName.equals(nameUpperFormat)) {
                			System.out.println("Cliente detectado en la BD");
                			if(clientData.length > 5) {
                				verMensaje("Usted ya tiene 2 tarjetas registradas, no puede registrar más");
                				dataValidateOk = false;
                				break;
                			} else {
                				
                			}
                		}
                		
                	}

                } catch (Exception ex) {
                	System.out.println("Error en la lectura del fichero: " + e);
                }
                
                if(dataValidateOk) {
                	System.out.println("Procediendo a la validación del DNI según el algoritmo.....");
                	nifValidateOk = validateNifAlgorithm(textNif.getText());
                }
                
                if(dataValidateOk) {
                	System.out.println("Procediendo a la validación del IBAN según el algoritmo.....");
                	bankAccountOk = validateBankAccountAlgorithm(textBankAccount.getText());
                }
                		

                if (dataValidateOk && nifValidateOk && bankAccountOk) {
                	System.out.println("Los datos del cliente y la comprobación del DNI según su algoritmo han sido pasados satisfactoriamente");
                    try {
                        FileWriter fw = new FileWriter(PATH_TO_FILE, true);
                        fw.write("Nombre: " + textNombre.getText() + " | "
                                + "NIF: " + textNif.getText() + " | Tarjeta Crédito: "
                                + textTarjetaCredito.getText() + " | IBAN bancario: " 
                                + textBankAccount.getText()+ "\n"
                                );

                        System.out.println("El cliente " + textNombre.getText() + " ha sido resgistrado en el sistema");
                        fw.close();
                    } catch (IOException ex) {
                        System.out.println("El cliente no ha podido ser guardado en clients.txt, el FileWriter ha fallado:\n" + ex);
                        System.exit(1);
                    }

                    verMensaje("Nombre: " + textNombre.getText() + "\n" + "NIF: " + textNif.getText() + "\n"
                            + "Tarjeta Crédito: " + textTarjetaCredito.getText() + "\n" 
                    		+ "IBAN bancario: " + textBankAccount.getText());

                    verMensaje("Registro guardado");
                    textNombre.setText("");
                    textNif.setText("");
                    textTarjetaCredito.setText("");
                    textBankAccount.setText("");
                } else {
                	verMensaje("Introduzca sus datos nuevamente");
                	System.out.println("Se inicializa la ventana con los valores a cadena vacía");
                    textNombre.setText("");
                    textNif.setText("");
                    textTarjetaCredito.setText("");
                    textBankAccount.setText("");
                }
            }
        });
        darAltaBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        darAltaBoton.setBounds(212, 192, 178, 23);
        contentPane.add(darAltaBoton);
        
        textBankAccount = new JTextField();
        textBankAccount.setColumns(10);
        textBankAccount.setBounds(212, 150, 178, 20);
        contentPane.add(textBankAccount);
        
        JLabel lblIbanBancario = new JLabel("IBAN BANCARIO");
        lblIbanBancario.setBounds(38, 146, 141, 26);
        contentPane.add(lblIbanBancario);
        
        JLabel infoMessage = new JLabel("<--- Nuevo para actividad de IBAN, resto del anterior, código aprovechado");
        infoMessage.setBounds(400, 147, 392, 26);
        contentPane.add(infoMessage);
        
        JLabel lblNewLabel_1 = new JLabel("(2 letras del país y 24 dígitos)");
        lblNewLabel_1.setBounds(10, 170, 169, 14);
        contentPane.add(lblNewLabel_1);
    }

    private void verMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private boolean validateClientData(String name, String nif, String creditCardNumber, String textBankAccount) {


        System.out.println("El DNI formateado es " + nif + "\nEl número de tarjeta de crédito formateado es " 
        + creditCardNumber + "\nEl número de IBAN introducido y ya formateado ha sido: " + textBankAccount);

        // Creo las Regex para los distintos datos
        String nameRegex = "^[\\p{L}]+(?:[\\p{Zs}][\\p{L}]+)*$";
        String nifRegex = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]$|^[xyzXYZ]\\d{7}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]$";
        String creditCardNumberRegex = "^(\\d\\s*){16}$";
        String bankAccountRegex = "^[eE][sS]\\d{24}$";
;

        // Creo un objeto Pattern compilando la expresión regular
        Pattern namePattern = Pattern.compile(nameRegex);
        Pattern nifPattern = Pattern.compile(nifRegex);
        Pattern creditCardNumberPattern = Pattern.compile(creditCardNumberRegex);
        Pattern bankAccountPattern = Pattern.compile(bankAccountRegex);

        // Crear un objeto Matcher y realizar la validación
        Matcher nameMatcher = namePattern.matcher(name);
        Matcher nifMatcher = nifPattern.matcher(nif);
        Matcher creditCardNumberMatcher = creditCardNumberPattern.matcher(creditCardNumber);
        Matcher bankAccountMatcher = bankAccountPattern.matcher(textBankAccount);

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
        
        if (!bankAccountMatcher.matches()) {
            message = "El número Iban de su cuenta bancaria  introducido " + textBankAccount + " no es correcto, recuerde insertar las 2 letras del país y los 24 dígitos";
            JOptionPane.showMessageDialog(null, message);
            System.out.println(message);
            checker = false;
        }
        
        System.out.println("Validate client data ok? = " + checker);
        return checker;
    }


    private boolean validateNifAlgorithm(String nif) {
        String[] lettersTable = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        if(nif.length() > 0) {
            if (nif.charAt(0) == 'X') {
                char[] nifCharArray = nif.toCharArray();
                nifCharArray[0] = '0';
                nif = new String(nifCharArray);
            } else if (nif.charAt(0) == 'Y') {
                char[] nifCharArray = nif.toCharArray();
                nifCharArray[0] = '1';
                nif = new String(nifCharArray);
            } else if (nif.charAt(0) == 'Z') {
                char[] nifCharArray = nif.toCharArray();
                nifCharArray[0] = '2';
                nif = new String(nifCharArray);
            }

            String letter = nif.substring(8);
            String upperLetter = letter.toUpperCase();
            Integer number = Integer.parseInt(nif.substring(0, 8));
            int rest = number % 23;
            
            System.out.println("Letra del DNI es : " + upperLetter);
            System.out.println("El número del DNI es: " + number);
            System.out.println("El resto de dividir " + number + " entre 23 es " + rest);

            if (lettersTable[rest].equals(upperLetter)) {
            	System.out.println("El DNI supera la prueba : " + lettersTable[rest] + " == " + upperLetter);
                return true;
            } else {
            	System.out.println("El DNI no supera la prueba de su algoritmo: ");
            	System.out.println(lettersTable[rest] + " y " + upperLetter + " son dstintas");
                return false;
            }
        } else {
        	System.out.println("El campo DNI ha llegado vacío al checkeo con algoritmo DNI");
        	return false;
        }
    }
    
    public boolean validateBankAccountAlgorithm(String bankAccount) {
    	
    	String IbanCountryLetters = bankAccount.substring(0, 2);
    	String IbanCountryLettersUppercase = IbanCountryLetters.toUpperCase();
    	System.out.println("El código del país introducido es " + IbanCountryLetters);
    	
    	if(!IbanCountryLettersUppercase.equals("ES")) {
    		System.out.println("Las letras del código de país no coinciden con ES de España");
    		verMensaje("Las letras de control de país en el IBAN indicado no pertenecen a España, por favor, vuelva a intentarlo");
    		return false;
    	} else {
    	return true;
    	}

    
    }
    
}
