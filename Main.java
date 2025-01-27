import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Main extends JFrame implements ActionListener {

  private static int _x;
  private static int _y;
  
  private JMenuBar menu;
  private JMenu mArchivo, mEdicion, mBuscar, mAyuda;
  private JMenuItem miNuevo, miAbrir, miGuardar, miCerrar, miSalir, miBuscar, miReemplazar, miACercaDe, miInsertarFila, miInsertarColumna, miEliminarFila, miEliminarColumna;
  private Container contentPane;
  
  private static List grid;
  
  private boolean isOpen;
  public static String focusIn;

  public Main(){    
    super("I-Square");
    setBounds((1024-600)/2, (768-300)/2, 600, 300);
    
    menu = new JMenuBar();
    setJMenuBar(menu);
    
    mArchivo = new JMenu("Archivo");
    menu.add(mArchivo);
    
    miNuevo = new JMenuItem("Nuevo");
    miNuevo.addActionListener(this);
    mArchivo.add(miNuevo);
    miAbrir = new JMenuItem("Abrir");
    miAbrir.addActionListener(this);
    mArchivo.add(miAbrir);
    miGuardar = new JMenuItem("Guardar");
    miGuardar.addActionListener(this);
    miGuardar.setEnabled(false);
    mArchivo.add(miGuardar);
    miCerrar = new JMenuItem("Cerrar");
    miCerrar.addActionListener(this);
    miCerrar.setEnabled(false);
    mArchivo.add(miCerrar);
    miSalir = new JMenuItem("Salir");
    miSalir.addActionListener(this);
    mArchivo.add(miSalir);
    
    mEdicion = new JMenu("Edicion");
    mEdicion.setEnabled(false);
    menu.add(mEdicion);
 
    miInsertarFila = new JMenuItem("Insertar Fila");
    miInsertarFila.addActionListener(this);
    mEdicion.add(miInsertarFila);
    miInsertarColumna = new JMenuItem("Insertar Columna");
    miInsertarColumna.addActionListener(this);
    mEdicion.add(miInsertarColumna);
    miEliminarFila = new JMenuItem("Eliminar Fila");
    miEliminarFila .addActionListener(this);
    mEdicion.add(miEliminarFila );
    miEliminarColumna = new JMenuItem("Eliminar Columna");
    miEliminarColumna .addActionListener(this);
    mEdicion.add(miEliminarColumna );
    
    mBuscar = new JMenu("Buscar");
    mBuscar.setEnabled(false);
    menu.add(mBuscar);
    
    miBuscar = new JMenuItem("Buscar");
    miBuscar.addActionListener(this);
    mBuscar.add(miBuscar);
    miReemplazar = new JMenuItem("Buscar y reemplazar");
    miReemplazar.addActionListener(this);
    mBuscar.add(miReemplazar);
    
    mAyuda = new JMenu("Ayuda");
    menu.add(mAyuda);
    
    miACercaDe = new JMenuItem("A cerca de...");
    miACercaDe.addActionListener(this);
    mAyuda.add(miACercaDe);
    
    isOpen = false;
    focusIn = "A1";
    
    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          dispose();
          System.exit(0);
        }
      }
    );
    setVisible(true);
  }

  public void newFile() {
    try {
      _x = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de columnas:", "10"));
      if (_x > 26) {
        JOptionPane.showMessageDialog(null, "El limite de columnas es 26", "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
        _x = 26;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Dato no permitido", "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
      _x = 10;
    }
    try {
      _y = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de filas:", "10"));
      if (_y > 26) {
        JOptionPane.showMessageDialog(null, "El limite de filas es 26", "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
        _y = 26;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Dato no permitido", "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
      _y = 10;
    }
    grid = new List();
    
    contentPane = getContentPane();
    contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
    
    add(new JLabel(""));
    
    int i;
    int j;
    for (i=1; i<=_x; i++) {
      add(new JLabel(" "+str(i)));
    }
    for (j=1; j<=_y; j++) {
      add(new JLabel(" "+j));
      for (i=1; i<=_x; i++) {
        Cell cell = new Cell(str(i)+j);
        grid.insert(cell);
        contentPane.add(cell);
      }
    }
    miGuardar.setEnabled(true);
    miCerrar.setEnabled(true);
    mEdicion.setEnabled(true);
    mBuscar.setEnabled(true);
    
    setVisible(true);
  }

  public void openFile() {
    String fileName;
    JFileChooser fileChooser = new JFileChooser();
    int fcFocus = fileChooser.showOpenDialog(this);
    if (fcFocus == fileChooser.APPROVE_OPTION) {
      try {
        fileName = fileChooser.getSelectedFile().getAbsolutePath();
        java.net.URL url = fileChooser.getSelectedFile().toURL();
        
        DataInputStream input = new DataInputStream(new FileInputStream(fileName));
        boolean moreCells = true;
        
        int size = input.readInt();
        _x = input.readInt();
        _y = input.readInt();
        String data[][] = new String[size][3];
        
        int i = 0;
        int j = 0;
        int k = 0;
        for (i=0; i<size; i++) {
          data[i][0] = input.readUTF();
          data[i][1] = input.readUTF();
          data[i][2] = input.readUTF();
        }
        grid = new List();
        grid.toEmpty();
        
        contentPane = getContentPane();
        contentPane.removeAll();
        contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
        
        add(new JLabel(""));
        for (i=1; i<=_x; i++) {
          add(new JLabel(" "+str(i)));
        }
        for (j=1; j<=_y; j++) {
          add(new JLabel(" "+j));
          for (i = 1; i <= _x; i++) {
            Cell cell = new Cell(str(i)+j);
            cell.value = data[k][1];
            cell.caption = data[k][2];
            grid.insert(cell);
            contentPane.add(cell);
            cell.setText(cell.caption);
            k++;
          }
        }
        input.close();
        
        isOpen = true;
        
        setVisible(true);
      } catch (Exception io){
        JOptionPane.showMessageDialog(null, "Error al abrir el documento" + io, "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    miGuardar.setEnabled(true);
    miCerrar.setEnabled(true);
    mEdicion.setEnabled(true);
    mBuscar.setEnabled(true);
    
    contentPane.repaint();
  }

  public void saveFile() {
    String fileName = "";
    JFileChooser fileChooser = new JFileChooser();
    int fcFocus = fileChooser.showSaveDialog(this);
    if (fcFocus == fileChooser.APPROVE_OPTION) {
      try {
        fileName = fileChooser.getSelectedFile().getAbsolutePath();
        if (fileName.substring(fileName.length()-3, fileName.length()).equals(".is")) {
          fileName += ".is";
        }
        DataOutputStream output = new DataOutputStream(new FileOutputStream(fileName));
        
        String data[][] = new String[grid.size()][3];
        data = grid.toCellArray();
        
        output.writeInt(grid.size());
        output.writeInt(_x);
        output.writeInt(_y);
        
        int i = 0;
        int j = 0;
        while (i < grid.size()) {
          for (j=0; j<=2; j++) {
            output.writeUTF(data[i][j]);
          }
          i++;
        }
        output.close();
      } catch (Exception io) {
        JOptionPane.showMessageDialog(null, "Error al guardar el documento", "Estas mal!", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }

  public void closeFile() {
    miGuardar.setEnabled(false);
    miCerrar.setEnabled(false);
    mEdicion.setEnabled(false);
    mBuscar.setEnabled(false);
    
    contentPane.removeAll();
    contentPane.repaint();
  }

  public void insertRow() {
    _y++;
    int row = _y(focusIn);
    int i;
    int j;
    int k;
    j = 1;
    for (i=_x*(row - 1); i<_x*row; i++) {
      grid.insert(new Cell("newCell"), _x*(row-1)+1);
      j++;
    }
    String data[][] = grid.toCellArray();
    
    grid.toEmpty();
    contentPane.removeAll();
    contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
    
    add(new JLabel(""));
    for (i=1; i<=_x; i++) {
      add(new JLabel(" " + str(i)));
    }
    k = 0;
    for (j=1; j<=_y; j++) {
      add(new JLabel(" " + j));
      for (i=1; i<=_x; i++) {
        Cell cell = new Cell(str(i) + j);
        cell.value = data[k][1];
        cell.caption = data[k][2];
        grid.insert(cell);
        add(cell);
        if (cell.value != "") {
          cell.setText(cell.caption);
        }
        k++;
      }
    }
    setVisible(true);
    contentPane.repaint();
  }

  public void insertCol() {
    _x++;
    int col = Integer.parseInt(val(_x(focusIn).charAt(0)));
    int i;
    int j;
    int k;
    j = col;
    for (i=_y*(col-1); i<_y*col; i++) {
      grid.insert(new Cell("newCell"), j);
      j += _x;
    }
    String data[][] = grid.toCellArray();
    
    grid.toEmpty();
    contentPane.removeAll();
    contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
    
    add(new JLabel(""));
    for (i=1; i<=_x; i++) {
      add(new JLabel(" " + str(i)));
    }
    k = 0;
    for (j=1; j<=_y; j++) {
      add(new JLabel(" " + j));
      for (i=1; i<=_x; i++) {
        Cell cell = new Cell(str(i)+j);
        cell.value = data[k][1];
        cell.caption = data[k][2];
        grid.insert(cell);
        add(cell);
        if (cell.value != "") {
          cell.setText(cell.caption);
        }
        k++;
      }
    }
    setVisible(true);
    contentPane.repaint();
  }

  public void deleteRow() {
    _y--;
    int row = _y(focusIn);
    int i;
    int j;
    int k;
    j = 1;
    for (i=_x*(row-1); i<_x*row; i++) {
      grid.delete(_x*(row-1) + 1);
      j++;
    }
    String data[][] = grid.toCellArray();
    
    grid.toEmpty();
    contentPane.removeAll();
    contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
    
    add(new JLabel(""));
    for (i=1; i<=_x; i++) {
      add(new JLabel(" " + str(i)));
    }
    k = 0;
    for (j=1; j<=_y; j++) {
      add(new JLabel(" " + j));
      for (i=1; i<=_x; i++) {
        Cell cell = new Cell(str(i) + j);
        cell.value = data[k][1];
        cell.caption = data[k][2];
        grid.insert(cell);
        add(cell);
        if (cell.value != "") {
          cell.setText(cell.caption);
        }
        k++;
      }
    }
    setVisible(true);
    contentPane.repaint();
  }

  public void deleteCol() {
    _x--;
    int col = Integer.parseInt(val(_x(focusIn).charAt(0)));
    int i;
    int j;
    int k;
    j = col;
    for (i=_y*(col - 1); i<_y*col; i++) {
      grid.delete(j);
      j += _x;
    }
    String data[][] = grid.toCellArray();
    
    grid.toEmpty();
    contentPane.removeAll();
    contentPane.setLayout(new GridLayout(_y+1, _x+1, 0, 0));
    
    add(new JLabel(""));
    for (i=1; i<=_x; i++) {
      add(new JLabel(" "+str(i)));
    }
    k = 0;
    for (j=1; j <=_y; j++) {
      add(new JLabel(" "+j));
      for (i=1; i<=_x; i++) {
        Cell cell = new Cell(str(i)+j);
        cell.value = data[k][1];
        cell.caption = data[k][2];
        grid.insert(cell);
        add(cell);
        if (cell.value != "") {
          cell.setText(cell.caption);
        }
        k++;
      }
    }
    setVisible(true);
    contentPane.repaint();
  }

  public static void find() {
    String word = JOptionPane.showInputDialog("Buscar:");
    grid.find(word);
  }
  
  public static void replace() {
    String caption = JOptionPane.showInputDialog("Buscar:");
    String newValue = JOptionPane.showInputDialog("Reemplazar por:");
    grid.replace(caption, newValue);
  }

  public static void aCercaDe() {
    JOptionPane.showMessageDialog(null, "Desarrollado por Wil C", "A cerca de I-Square ®", JOptionPane.INFORMATION_MESSAGE);
  }

  public static String str(int index) {
    index--;
    String letter[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    return letter[index];
  }

  public static String val(char index) {
    index--;
    String letter[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
    return letter[index - 64];
  }

  public static String trim(String expression) {
    String newExpression = "";
    int i;
    for (i=0; i<expression.length(); i++) {
      if (expression.charAt(i) != ' ' && expression.charAt(i) != "'".charAt(0) && expression.charAt(i) != '&' && expression.charAt(i) != '#' && expression.charAt(i) != '=' && expression.charAt(i) != '{' && expression.charAt(i) != '[' && expression.charAt(i) != '(' && expression.charAt(i) != ')' && expression.charAt(i) != ']' && expression.charAt(i) != '}') {
        newExpression += expression.charAt(i);
      }
    }
    return newExpression;
  }

  public static String _x(String name) {
    String x = "";
    int i;
    for (i=0; i<name.length(); i++) {
      try {
        Integer.parseInt(name.charAt(i)+"");
      } catch (Exception e) {
        x += name.charAt(i);
      }
    }
    return (x);
  }

  public static int _y(String name) {
    String y = "";
    int i;
    for (i=0; i<name.length(); i++) {
      try {
        Integer.parseInt(name.charAt(i)+"");
        y += name.charAt(i);
      } catch (Exception e) {
      }
    }
    return (Integer.parseInt(y));
  }

  public static String cellsToValue(String cells) {
    String number = "";
    String value = "";
    int i;
    int j;
    for (i=0; i<cells.length(); i++) {
      try {
        if (cells.charAt(i) != '+' && cells.charAt(i) != '-' && cells.charAt(i) != '*' && cells.charAt(i) != '/') {
          Integer.parseInt(cells.charAt(i)+"");
        }
        value += cells.charAt(i);
      } catch (Exception e) {
        i++;
        for (j=i; j<cells.length(); j++) {
          if (cells.charAt(j) == '+' || cells.charAt(j) == '-' || cells.charAt(j) == '*' || cells.charAt(j) == '/') {
            break;
          }
        }
        number = cells.substring(i-1, j);
        number = grid.nameToCaption(number);
        value += number;
      }
    }
    if (number != "") {
      return value;
    } else {
      return cells;
    }
  }

  public static String getCaption(String expression) {
    expression = trim(expression);
    expression = cellsToValue(expression);
    boolean begin = true;
    char operator = ' ';
    int caption = 0;
    String number = "";
    int i = expression.length();
    for (i=0; i<=expression.length()-1; i++) {
      try {
        Integer.parseInt(expression.charAt(i)+"");
        if (expression.charAt(i) != '+' && expression.charAt(i) != '-' && expression.charAt(i) != '*' && expression.charAt(i) != '/') {
          number += expression.charAt(i);
        }
      } catch (Exception e) {
        if (begin == true) {
          if (number == "") {
            number = "0";
          }
          caption = Integer.parseInt(number);
          begin = false;
        } else {
          switch (operator) {
            case '+':
              caption += Integer.parseInt(number);
              break;
            case '-':
              caption -= Integer.parseInt(number);
              break;
            case '*':
              caption *= Integer.parseInt(number);
              break;
            case '/':
              caption /= Integer.parseInt(number);
              break;
          }
        }
        number = "";
        operator = expression.charAt(i);
      }
    }
    switch (operator) {
      case '+':
        caption += Integer.parseInt(number);
        break;
      case '-':
        caption -= Integer.parseInt(number);
        break;
      case '*':
        caption *= Integer.parseInt(number);
        break;
      case '/':
        caption /= Integer.parseInt(number);
        break;
    }
    if (caption == 0) {
      return expression;
    } else {
      return caption + "";
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == miNuevo) {
      if (isOpen == true) {
        closeFile();
      }
      newFile();
      isOpen = true;
    } else if (e.getSource() == miAbrir) {
      openFile();
    } else if (e.getSource() == miGuardar) {
      saveFile();
    } else if (e.getSource() == miCerrar) {
      closeFile();
      isOpen = false;
    } else if (e.getSource() == miSalir) {
      System.exit(0);
    } else if (e.getSource() == miInsertarFila) {
      insertRow();
    } else if (e.getSource() == miInsertarColumna) {
      insertCol();
    } else if (e.getSource() == miEliminarFila) {
      deleteRow();
    } else if (e.getSource() == miEliminarColumna) {
      deleteCol();
    } else if (e.getSource() == miBuscar) {
      find();
    } else if (e.getSource() == miReemplazar) {
      replace();
    } else if (e.getSource() == miACercaDe) {
      aCercaDe();
    }
  }
  
  public static void main(String args[]) {
    new Main();
  } 
  
}
