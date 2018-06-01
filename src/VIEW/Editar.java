/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import ClientApi.BasicConfig;
import Model.Categoria;
import Model.Producto;
import Model.TipoProducto;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Gustavo
 */
public class Editar extends javax.swing.JFrame {

    public static ArrayList<File> imagenes;
    public static File imagenPrincipal;
    private String idProducto;
    private TipoProducto[] tipos;
    private Categoria[] categorias;
    Producto p;

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Editar(String _idProducto) {
        initComponents();
        getContentPane().setBackground(Color.GREEN); 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setIdProducto(_idProducto);

        if (idProducto == null) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            BuscarProducto bs = new BuscarProducto();
            bs.setVisible(true);
            dispose();
            return;
        }
        
        
        imagenes = new ArrayList();
        WebTarget service = BasicConfig.run();
        String path = "Productos/" + idProducto + "/null/null";
        System.out.println(path);
        String response = service.path(path).request().get(String.class);
        if (response.equals("[]")) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        p = Producto.stringToProducto2(response);

        if (p == null) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            System.out.println("no pasa na");
            return;
        }
        
        setJMenuBar(menuBarCreator(this));
        tf_nombre.setText(p.getNombre());
        tf_cantidad.setText(p.getCantidad());
        tf_precio.setText(p.getPrecio());
        ta_descripcion.setText(p.getDescripcions());

        //cargar Tipo PRoducto
        //Create a Todo
        path = "TipoProducto";
        response = service.path(path).request().get(String.class);
        if (response.equals("[]")) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] values = response.split("},");
        TipoProducto[] list = new TipoProducto[values.length];

        for (int i = 0; i < values.length; i++) {
            list[i] = new TipoProducto().stringToTipoProducto(values[i]);
        }

        tipos = list;
        jc_tipoProducto.removeAllItems();
        jc_tipoProducto.addItem("Seleccione");
        for (TipoProducto list1 : list) {
            jc_tipoProducto.addItem(list1.getNombre());
        }

        String nombreTipo = "";

        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].getId() == Integer.parseInt(p.getTipo())) {
                nombreTipo = tipos[i].getNombre();
                break;
            }
        }

        //cargar Categoria
        jc_categoria.removeAllItems();
        jc_categoria.addItem("Seleccione");

        for (int i = 0; i < jc_tipoProducto.getItemCount(); i++) {
            if (nombreTipo.equals(jc_tipoProducto.getItemAt(i))) {
                jc_tipoProducto.setSelectedIndex(i);
                cargarCategorias(String.valueOf(i));
            }
        }

        String nombreCategoria = "";

        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].getId() == Integer.parseInt(p.getCategoria())) {
                nombreCategoria = categorias[i].getNombre();
            }
        }

        for (int i = 0; i < jc_categoria.getItemCount(); i++) {
            if (jc_categoria.getItemAt(i).equals(nombreCategoria)) {
                jc_categoria.setSelectedIndex(i);
            }
        }

        cargarImagen();
    }

    /**
     * Creates new form Editar
     */
    public Editar() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        if (idProducto == null) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            BuscarProducto bs = new BuscarProducto();
            bs.setVisible(true);
            System.out.println("no pasa na");
            return;
        }
        imagenes = new ArrayList();
        WebTarget service = BasicConfig.run();
        String path = "Productos/" + idProducto + "/null/null";
        System.out.println(path);
        String response = service.path(path).request().get(String.class);
        if (response.equals("[]")) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            return;
        }

        p = Producto.stringToProducto(response);

        if (p == null) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tf_nombre.setText(p.getNombre());
        tf_cantidad.setText(p.getPrecio());
        tf_precio.setText(p.getCantidad());
        ta_descripcion.setText(p.getDescripcions());

        //cargar Tipo PRoducto
        //Create a Todo
        path = "TipoProducto";
        response = service.path(path).request().get(String.class);
        if (response.equals("[]")) {
            JOptionPane.showMessageDialog(null, "No se han encontrado coincidencias", "InfoBox: Sin resultados", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] values = response.split("},");
        TipoProducto[] list = new TipoProducto[values.length];

        for (int i = 0; i < values.length; i++) {
            list[i] = new TipoProducto().stringToTipoProducto(values[i]);
        }

        tipos = list;
        jc_tipoProducto.removeAllItems();
        jc_tipoProducto.addItem("Seleccione");
        for (TipoProducto list1 : list) {
            jc_tipoProducto.addItem(list1.getNombre());
        }

        String nombreTipo = "";

        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].getId() == Integer.parseInt(p.getTipo())) {
                nombreTipo = tipos[i].getNombre();
                break;
            }
        }

        //cargar Categoria
        jc_categoria.removeAllItems();
        jc_categoria.addItem("Seleccione");

        for (int i = 0; i < jc_tipoProducto.getItemCount(); i++) {
            if (nombreTipo.equals(jc_tipoProducto.getItemAt(i))) {
                jc_tipoProducto.setSelectedIndex(i);
                cargarCategorias(String.valueOf(i));
            }
        }

        String nombreCategoria = "";

        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].getId() == Integer.parseInt(p.getCategoria())) {
                nombreCategoria = categorias[i].getNombre();
            }
        }

        for (int i = 0; i < jc_categoria.getItemCount(); i++) {
            if (jc_categoria.getItemAt(i).equals(nombreCategoria)) {
                jc_categoria.setSelectedIndex(i);
            }
        }

    }
    
    private JMenuBar menuBarCreator(javax.swing.JFrame _buscar) {
        // create the menu parts
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.GREEN); 
        JMenu menuFile = new JMenu("Opciones");

        JMenuItem menuFileWebsite = new JMenuItem("Home");
        JMenuItem menuProducto = new JMenuItem("Buscar Producto");
        JMenuItem menuHelpRules = new JMenuItem("Editar Perfil");
        JMenuItem menuHelpAbout = new JMenuItem("Cerrar Sesión");

        // website button action
        menuFileWebsite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Home().setVisible(true);
                _buscar.setVisible(false);
            }
        });
        
        menuProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BuscarProducto().setVisible(true);
                _buscar.setVisible(false);
            }
        });
        
        menuHelpRules.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EditarPerfil().setVisible(true);
                _buscar.setVisible(false);
            }
        });
        
        menuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                _buscar.setVisible(false);
            }
        });
        // make the shortcuts for the items
        menuFile.setMnemonic(KeyEvent.VK_F);

        // put the menu parts with eachother
        menuBar.add(menuFile);

        menuFile.add(menuFileWebsite);
        menuFile.add(menuHelpRules);
        menuFile.add(menuProducto);
        menuFile.add(menuHelpAbout);

        return menuBar;
    }

    public void cargarImagen() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                try {
                    WebTarget service = BasicConfig.run();
                    String _path = "imagen/" + p.getId();
                    String path = service.path(_path).request().get(String.class);
                    System.out.println("Get Image from " + path.replace("\"", ""));
                    path = path.replace("\"", "");
                    path = path.replace(" ", "%20");
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    image = resize(image, 150, 120);
                    jLabelImagenPrincipal.setIcon(new ImageIcon(image));
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

            }
        });
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void modificarProducto(Producto _p) {
        try {
            WebTarget service = BasicConfig.run();
            Form form = new Form();
            form.param("nombre", _p.getNombre());
            form.param("cantidad", _p.getCantidad());
            form.param("tipo_producto", _p.getTipo());
            form.param("precio", _p.getPrecio());
            form.param("categoria", _p.getCategoria());
            form.param("descripcion", _p.getDescripcions().replace("\\n", "").replace("\n", "").replace("\\", ""));

            String path = "Productos/" + _p.getId();
            System.out.println(path);
            String response = service.path(path).request().put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);

            JOptionPane.showMessageDialog(null, "Producto modificado con exito");
            this.setVisible(false);
            new BuscarProducto().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error por favor vuelva a intentarlo " + "\n" + "Descripcion del error: " + ex.getMessage(), "InfoBox: Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tf_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabelImagenPrincipal = new javax.swing.JLabel();
        jc_categoria = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jc_tipoProducto = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabelImagenPrincipal1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_descripcion = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        lblogo1 = new javax.swing.JLabel();
        tf_cantidad = new javax.swing.JFormattedTextField();
        tf_precio = new javax.swing.JFormattedTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editar Producto");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Frutos Frescos");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre Producto:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tipo Producto:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Precio:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Stock:");

        jLabelImagenPrincipal.setMaximumSize(new java.awt.Dimension(20, 20));

        jc_categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCancelar.setBackground(new java.awt.Color(102, 255, 0));
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(153, 153, 0));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jc_tipoProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jc_tipoProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jc_tipoProductoItemStateChanged(evt);
            }
        });
        jc_tipoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jc_tipoProductoActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Categoría:");

        jLabelImagenPrincipal1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelImagenPrincipal1.setText("Imagen Principal:");
        jLabelImagenPrincipal1.setMaximumSize(new java.awt.Dimension(20, 20));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Descirpción");

        ta_descripcion.setColumns(20);
        ta_descripcion.setRows(5);
        jScrollPane2.setViewportView(ta_descripcion);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("© Todos los derechos reservados");

        lblogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo.png"))); // NOI18N
        lblogo1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        tf_cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##0"))));

        tf_precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##0"))));

        jMenuBar1.setBackground(new java.awt.Color(102, 255, 0));

        jMenu1.setText("Inicio");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Listar");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Buscar");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Cerrar Sesión");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(265, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(227, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelImagenPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jc_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jc_tipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelImagenPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tf_precio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addComponent(tf_cantidad, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(153, 153, 153)
                .addComponent(btnGuardar)
                .addGap(140, 140, 140))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblogo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jc_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jc_tipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tf_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tf_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelImagenPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelImagenPrincipal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(lblogo1)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
        new BuscarProducto().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private boolean stringToDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (tf_nombre.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Error ingrese el nombre", "InfoBox: Complete los campos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tf_cantidad.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Error ingrese la cantidad", "InfoBox: Complete los campos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Integer.parseInt(tf_cantidad.getText()) > 10001) {
            JOptionPane.showMessageDialog(null, "Cantidad maxima superadas [cantidad maxima: 10.000]", "InfoBox: Valor maximo excedido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tf_precio.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Error ingrese el precio", "InfoBox: Complete los campos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (Double.parseDouble(tf_precio.getText()) <= 99) {
            JOptionPane.showMessageDialog(null, "Precio insuficiente [Precio minimo: 100]", "InfoBox: Valor insuficiente", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!stringToDouble(tf_cantidad.getText())) {
            JOptionPane.showMessageDialog(null, "Solo ingrese numeros en cantidad", "InfoBox: Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!stringToDouble(tf_precio.getText())) {
            JOptionPane.showMessageDialog(null, "Solo ingrese numeros en precio", "InfoBox: Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idTipo, idCategoria;
        Producto _p = new Producto();
        String cantida = tf_cantidad.getText();
        String precio = tf_precio.getText();
        _p.setId(this.p.getId());
        _p.setNombre(tf_nombre.getText());
        _p.setCantidad(cantida);
        _p.setPrecio(precio);
        _p.setDescripcions(ta_descripcion.getText());
        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].getNombre().equals(jc_tipoProducto.getSelectedItem())) {
                _p.setTipo(tipos[i].getId() + "");
            }
        }

        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].getNombre().equals(jc_categoria.getSelectedItem())) {
                _p.setCategoria(categorias[i].getId() + "");
            }
        }
        modificarProducto(_p);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jc_tipoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jc_tipoProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jc_tipoProductoActionPerformed

    private void jc_tipoProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jc_tipoProductoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selected = (String) evt.getItem();
            String idProducto = null;
            jc_categoria.removeAllItems();
            jc_categoria.addItem("Seleccione");
            for (TipoProducto tipo : tipos) {
                if (tipo.getNombre().equalsIgnoreCase(selected)) {
                    idProducto = String.valueOf(tipo.getId());
                }
            }
            if (idProducto != null) {
                cargarCategorias(idProducto);
            }
        }


    }//GEN-LAST:event_jc_tipoProductoItemStateChanged

    public void cargarCategorias(String idProducto) {
        //cargar Categoria
        jc_categoria.removeAllItems();
        jc_categoria.addItem("Seleccione");
        WebTarget service = BasicConfig.run();
        //Create a Todo
        String path = "Categoria/" + idProducto;
        String response = service.path(path).request().get(String.class);
        String[] values = response.split("},");
        categorias = new Categoria[values.length];
        for (int i = 0; i < values.length; i++) {
            categorias[i] = new Categoria().stringToCategoria(values[i]);
        }

        for (Categoria _categoria : categorias) {
            jc_categoria.addItem(_categoria.getNombre());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Editar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelImagenPrincipal;
    private javax.swing.JLabel jLabelImagenPrincipal1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jc_categoria;
    private javax.swing.JComboBox<String> jc_tipoProducto;
    private javax.swing.JLabel lblogo1;
    private javax.swing.JTextArea ta_descripcion;
    private javax.swing.JFormattedTextField tf_cantidad;
    private javax.swing.JTextField tf_nombre;
    private javax.swing.JFormattedTextField tf_precio;
    // End of variables declaration//GEN-END:variables
}
