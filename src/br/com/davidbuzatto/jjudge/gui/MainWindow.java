/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author David
 */
public class MainWindow extends javax.swing.JFrame {

    private DefaultListModel<File> listPackagesModel;
    private DefaultListModel<TestSet> listTestSetsModel;
    private List<TestSet> testSets;
    private int secondsToTimeout;
    private boolean outputStreams;
    
    /**
     * Creates new form JJudge
     */
    public MainWindow() {
        initComponents();
        customInit();
    }

    private void customInit() {
        
        secondsToTimeout = 5;
        outputStreams = false;
        
        setIconImage( new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/user_gray.png" ) ).getImage() );
        
        listPackagesModel = new DefaultListModel<>();
        listTestSetsModel = new DefaultListModel<>();
        
        listPackages.setModel( listPackagesModel );
        listTestSets.setModel( listTestSetsModel );
        
        lblStatus.setText( "" );
        
        DefaultCaret caret = (DefaultCaret) textPaneProcessOutput.getCaret();
        caret.setUpdatePolicy( DefaultCaret.ALWAYS_UPDATE );
        
        //testSets = Utils.loadTestSets();
        //buildTestSetsModel();
        
        //listPackagesModel.addElement( new File( "debugPackageC.zip" ) );
        //listPackagesModel.addElement( new File( "debugPackageCPP.zip" ) );
        //listPackagesModel.addElement( new File( "debugPackageJAVA.zip" ) );
        //listPackagesModel.addElement( new File( "debugPackagePYTHON.zip" ) );
        
    }
    
    private void buildTestSetsModel() {
        listTestSetsModel.clear();
        for ( TestSet ts : testSets ) {
            listTestSetsModel.addElement( ts );
        }
    }
    
    private void buildTestPackage() {
        
        String name = JOptionPane.showInputDialog( "Student full name:" );
        String code;
        String packageName;
        
        if ( name != null ) {
            
            name = name.trim();
            
            code = JOptionPane.showInputDialog( "Student code:" );
            
            if ( code != null ) {
                
                code = code.trim();
                
                packageName = JOptionPane.showInputDialog( "Package name:" );
                
                if ( packageName != null ) {
                    
                    packageName = packageName.trim();
                    
                    JFileChooser jfc = new JFileChooser( new File( Utils.getPref( "buildTestPackagePath" ) ) );
                    jfc.setDialogTitle( "Choose the files to insert into the test package" );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
                    jfc.removeChoosableFileFilter( jfc.getFileFilter() );
                    jfc.setFileFilter( new FileNameExtensionFilter( "Source code files" , "c", "cpp", "java", "py" ) );

                    jfc.showOpenDialog( this );
                    File[] files = jfc.getSelectedFiles();

                    if ( files != null && files.length > 0 ) {

                        String absolutePath = files[0].getParentFile().getAbsolutePath();
                        Utils.setPref( "buildTestPackagePath", absolutePath );
                        
                        Student s = new Student();
                        s.setName( name );
                        s.setCode( code );

                        try {
                            
                            File studentFile = new File( absolutePath + "/student.json" );
                            
                            Utils.saveStudent( s, studentFile );
                            
                            List<File> filesList = new ArrayList<>( Arrays.asList( files ) );
                            filesList.add( studentFile );
                            files = filesList.toArray( files );
                            
                            Utils.zipFiles( files, new File( absolutePath + "/" + packageName + ".zip" )  );
                            
                            studentFile.delete();
                            
                            JOptionPane.showMessageDialog( this, "The test package was built successfully!" );
                            
                        } catch ( IOException exc )  {
                            exc.printStackTrace();
                        }

                    }
                
                }
        
            }
            
        }
        
    }
    
    private void loadTestSet() {
        
        JFileChooser jfc = new JFileChooser( new File( Utils.getPref( "loadTestSets" ) ) );
        jfc.setDialogTitle( "Choose a package of test sets" );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( "Test Sets JSON File" , "json" ) );
        
        if ( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            
            File file = jfc.getSelectedFile();

            if ( file != null ) {
                Utils.setPref( "loadTestSets", file.getParentFile().getAbsolutePath() );
                testSets = Utils.loadTestSets( file );
                buildTestSetsModel();
            }
            
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        menuItemShowDetails = new javax.swing.JMenuItem();
        panelPackages = new javax.swing.JPanel();
        scrollPackages = new javax.swing.JScrollPane();
        listPackages = new javax.swing.JList<>();
        btnBuildTestPackage = new javax.swing.JButton();
        btnAddPackage = new javax.swing.JButton();
        btnRemovePackage = new javax.swing.JButton();
        panelTestSets = new javax.swing.JPanel();
        scrollTestSets = new javax.swing.JScrollPane();
        listTestSets = new javax.swing.JList<>();
        lblStatus = new javax.swing.JLabel();
        btnLoadTestSet = new javax.swing.JButton();
        btnRunTest = new javax.swing.JButton();
        checkGenerateResultsSpreadsheet = new javax.swing.JCheckBox();
        panelProcessOutput = new javax.swing.JPanel();
        scrollProcessOutput = new javax.swing.JScrollPane();
        textPaneProcessOutput = new javax.swing.JTextPane();
        panelResults = new javax.swing.JPanel();
        scrollResults = new javax.swing.JScrollPane();
        resultPanel = new br.com.davidbuzatto.jjudge.gui.ResultPanel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemBuildTestPackage = new javax.swing.JMenuItem();
        sepMenuFile02 = new javax.swing.JPopupMenu.Separator();
        menuItemLoadTestSets = new javax.swing.JMenuItem();
        sepMenuFile01 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuItemTestSets = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemHowTo = new javax.swing.JMenuItem();
        sepMenuHelp01 = new javax.swing.JPopupMenu.Separator();
        menuItemAbout = new javax.swing.JMenuItem();

        menuItemShowDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/report.png"))); // NOI18N
        menuItemShowDetails.setText("Show details...");
        menuItemShowDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemShowDetailsActionPerformed(evt);
            }
        });
        popupMenu.add(menuItemShowDetails);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JJudge");

        panelPackages.setBorder(javax.swing.BorderFactory.createTitledBorder("Package(s) or File(s) to Test"));

        scrollPackages.setViewportView(listPackages);

        btnBuildTestPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/package.png"))); // NOI18N
        btnBuildTestPackage.setText("Build");
        btnBuildTestPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuildTestPackageActionPerformed(evt);
            }
        });

        btnAddPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/add.png"))); // NOI18N
        btnAddPackage.setText("Add");
        btnAddPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPackageActionPerformed(evt);
            }
        });

        btnRemovePackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/delete.png"))); // NOI18N
        btnRemovePackage.setText("Remove");
        btnRemovePackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePackageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPackagesLayout = new javax.swing.GroupLayout(panelPackages);
        panelPackages.setLayout(panelPackagesLayout);
        panelPackagesLayout.setHorizontalGroup(
            panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPackagesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPackages, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPackagesLayout.createSequentialGroup()
                        .addComponent(btnBuildTestPackage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddPackage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemovePackage)))
                .addContainerGap())
        );
        panelPackagesLayout.setVerticalGroup(
            panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPackagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPackages, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemovePackage)
                    .addComponent(btnAddPackage)
                    .addComponent(btnBuildTestPackage))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTestSets.setBorder(javax.swing.BorderFactory.createTitledBorder("Test Set(s) (right click each item for details)"));

        listTestSets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTestSets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listTestSetsMousePressed(evt);
            }
        });
        scrollTestSets.setViewportView(listTestSets);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 102, 204));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStatus.setText("status");

        btnLoadTestSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_refresh.png"))); // NOI18N
        btnLoadTestSet.setText("Load Test Sets");
        btnLoadTestSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadTestSetActionPerformed(evt);
            }
        });

        btnRunTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/accept.png"))); // NOI18N
        btnRunTest.setText("Run Test");
        btnRunTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunTestActionPerformed(evt);
            }
        });

        checkGenerateResultsSpreadsheet.setText("Generate Results Spreadsheet");

        javax.swing.GroupLayout panelTestSetsLayout = new javax.swing.GroupLayout(panelTestSets);
        panelTestSets.setLayout(panelTestSetsLayout);
        panelTestSetsLayout.setHorizontalGroup(
            panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestSetsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTestSetsLayout.createSequentialGroup()
                        .addComponent(scrollTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelTestSetsLayout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRunTest))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTestSetsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(checkGenerateResultsSpreadsheet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadTestSet)))
                .addContainerGap())
        );
        panelTestSetsLayout.setVerticalGroup(
            panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestSetsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadTestSet)
                    .addComponent(checkGenerateResultsSpreadsheet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRunTest)
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelProcessOutput.setBorder(javax.swing.BorderFactory.createTitledBorder("Process Output"));

        textPaneProcessOutput.setEditable(false);
        textPaneProcessOutput.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scrollProcessOutput.setViewportView(textPaneProcessOutput);

        javax.swing.GroupLayout panelProcessOutputLayout = new javax.swing.GroupLayout(panelProcessOutput);
        panelProcessOutput.setLayout(panelProcessOutputLayout);
        panelProcessOutputLayout.setHorizontalGroup(
            panelProcessOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollProcessOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProcessOutputLayout.setVerticalGroup(
            panelProcessOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollProcessOutput)
                .addContainerGap())
        );

        panelResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

        resultPanel.setFocusable(false);

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        scrollResults.setViewportView(resultPanel);

        javax.swing.GroupLayout panelResultsLayout = new javax.swing.GroupLayout(panelResults);
        panelResults.setLayout(panelResultsLayout);
        panelResultsLayout.setHorizontalGroup(
            panelResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollResults)
                .addContainerGap())
        );
        panelResultsLayout.setVerticalGroup(
            panelResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollResults)
                .addContainerGap())
        );

        menuFile.setMnemonic('F');
        menuFile.setText("File");

        menuItemBuildTestPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/package.png"))); // NOI18N
        menuItemBuildTestPackage.setMnemonic('B');
        menuItemBuildTestPackage.setText("Build Test Package");
        menuItemBuildTestPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBuildTestPackageActionPerformed(evt);
            }
        });
        menuFile.add(menuItemBuildTestPackage);
        menuFile.add(sepMenuFile02);

        menuItemLoadTestSets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_refresh.png"))); // NOI18N
        menuItemLoadTestSets.setMnemonic('L');
        menuItemLoadTestSets.setText("Load Test Sets");
        menuItemLoadTestSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadTestSetsActionPerformed(evt);
            }
        });
        menuFile.add(menuItemLoadTestSets);
        menuFile.add(sepMenuFile01);

        menuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/door_out.png"))); // NOI18N
        menuItemExit.setMnemonic('x');
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        menuEdit.setMnemonic('E');
        menuEdit.setText("Edit");

        menuItemTestSets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_edit.png"))); // NOI18N
        menuItemTestSets.setMnemonic('T');
        menuItemTestSets.setText("Test Sets");
        menuItemTestSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTestSetsActionPerformed(evt);
            }
        });
        menuEdit.add(menuItemTestSets);

        menuBar.add(menuEdit);

        menuHelp.setMnemonic('H');
        menuHelp.setText("Help");

        menuItemHowTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/help.png"))); // NOI18N
        menuItemHowTo.setMnemonic('c');
        menuItemHowTo.setText("How to configure and use");
        menuItemHowTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHowToActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemHowTo);
        menuHelp.add(sepMenuHelp01);

        menuItemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/information.png"))); // NOI18N
        menuItemAbout.setMnemonic('A');
        menuItemAbout.setText("About...");
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPackages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelProcessOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelPackages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProcessOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPackageActionPerformed
        
        JFileChooser jfc = new JFileChooser( new File( Utils.getPref( "addPackagePath" ) ) );
        jfc.setDialogTitle( "Choose a package or a source code file" );
        jfc.setMultiSelectionEnabled( true );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( 
                "Test Package (Zip) or Source code" , 
                "zip", "c", "cpp", "java", "py" ) );
        
        
        if ( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            
            File[] files = jfc.getSelectedFiles();
        
            if ( files != null && files.length > 0 ) {

                Utils.setPref( "addPackagePath", files[0].getParentFile().getAbsolutePath() );

                Arrays.sort( files, ( File f1, File f2 ) -> {

                    String n1 = f1.getName();
                    String n2 = f2.getName();

                    if ( ( n1.startsWith( "ex" ) || n1.startsWith( "de" ) || n1.startsWith( "pr" ) ) && 
                         ( n2.startsWith( "ex" ) || n2.startsWith( "de" ) || n2.startsWith( "pr" ) ) && 
                           n1.contains( "." ) && n2.contains( "." ) ) {

                        String t1 = n1.substring( 0, n1.indexOf( "." ) );
                        String t2 = n2.substring( 0, n2.indexOf( "." ) );

                        String v1;
                        String v2;

                        try {
                            v1 = n1.substring( n1.indexOf( "." ) + 1, n1.lastIndexOf( "." ) );
                            v2 = n2.substring( n2.indexOf( "." ) + 1, n2.lastIndexOf( "." ) );
                        } catch ( StringIndexOutOfBoundsException exc ) {
                            return n1.compareTo( n2 );
                        }

                        try {

                            int num1 = Integer.parseInt( v1 );
                            int num2 = Integer.parseInt( v2 );

                            int compT = t1.compareTo( t2 );

                            if ( compT == 0 ) {
                                return num1 - num2;
                            } else {
                                return compT;
                            }

                        } catch ( NumberFormatException exc ) {
                            return n1.compareTo( n2 );
                        }

                    } else {
                        return n1.compareTo( n2 );
                    }

                });

                for ( File f : files ) {
                    listPackagesModel.addElement( f );
                }
                
            }
        
        }
        
    }//GEN-LAST:event_btnAddPackageActionPerformed

    private void btnRemovePackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePackageActionPerformed
        
        if ( listPackages.getSelectedValue() != null ) {
            
            if ( JOptionPane.showConfirmDialog( 
                    this, "Do you really want to remove the selected package(s)/file(s)?",
                    "Remove package/file",
                    JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
                
                int[] indices = listPackages.getSelectedIndices();
                
                for ( int i = indices.length-1; i >= 0; i-- ) {
                    listPackagesModel.remove( indices[i] );
                }
                
            }
            
        }
        
    }//GEN-LAST:event_btnRemovePackageActionPerformed

    private void btnRunTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunTestActionPerformed
        
        final TestSet tSet;
        
        if ( listTestSetsModel.getSize() == 1 ) {
            tSet = listTestSetsModel.get( 0 );
        } else {
            tSet = listTestSets.getSelectedValue();
        }
        List<TestSetResult> tSetResList = new ArrayList<>();
        
        resultPanel.setTestSetResultList( tSetResList );
        resultPanel.generateRects();
        resultPanel.repaint();
        resultPanel.updateSize();
        scrollResults.updateUI();
        
        if ( tSet != null && !listPackagesModel.isEmpty() ) {
            
            new Thread( new Runnable() {
                
                @Override
                public void run() {
                    
                    listPackages.setEnabled( false );
                    listTestSets.setEnabled( false );
                    btnBuildTestPackage.setEnabled( false );
                    btnAddPackage.setEnabled( false );
                    btnRemovePackage.setEnabled( false );
                    checkGenerateResultsSpreadsheet.setEnabled( false );
                    btnLoadTestSet.setEnabled( false );
                    btnRunTest.setEnabled( false );
                    menuItemBuildTestPackage.setEnabled( false );
                    menuItemLoadTestSets.setEnabled( false );
                    menuItemExit.setEnabled( false );
                    menuItemTestSets.setEnabled( false );
                    menuItemHowTo.setEnabled( false );
                    menuItemAbout.setEnabled( false );
                    lblStatus.setText( "Please wait while the tests are being executed." );
                    textPaneProcessOutput.setText( "" );
                    resultPanel.setMouseOverAllowed( false );
                    
                    try {
                        
                        for ( int i = 0; i < listPackagesModel.size(); i++ ) {
                            
                            File file = listPackagesModel.get( i );
                            
                            Utils.addFormattedText( 
                                    textPaneProcessOutput, 
                                    String.format( "Processing %s\n", file ),
                                    Color.BLUE, false );

                            tSetResList.add( Utils.runTest( 
                                    file, 
                                    tSet, 
                                    outputStreams, 
                                    secondsToTimeout, 
                                    textPaneProcessOutput ) );

                            resultPanel.generateRects();
                            resultPanel.updateSize();
                            resultPanel.repaint();
                            scrollResults.updateUI();
                                
                            if ( file.getName().endsWith( ".zip" ) ) {

                                Utils.addFormattedText( 
                                        textPaneProcessOutput, 
                                        String.format( "Cleaning up...\n\n", file ),
                                        Color.BLACK, false );

                                FileUtils.deleteDirectory( new File( 
                                        file.getAbsolutePath().replace( ".zip", "" ).trim() ) );
                                
                            }
                            
                        }
                        
                        if ( checkGenerateResultsSpreadsheet.isSelected() ) {
                            Utils.processResultsToExcel( tSetResList, tSet );
                        }
                        
                    } catch ( IOException | InterruptedException exc ) {
                        exc.printStackTrace();
                    }
                    
                    Utils.addFormattedText( 
                            textPaneProcessOutput, 
                            "Finished!!!",
                            Color.BLACK, false );
                    
                    listPackages.setEnabled( true );
                    listTestSets.setEnabled( true );
                    btnBuildTestPackage.setEnabled( true );
                    btnAddPackage.setEnabled( true );
                    btnRemovePackage.setEnabled( true );
                    checkGenerateResultsSpreadsheet.setEnabled( true );
                    btnLoadTestSet.setEnabled( true );
                    btnRunTest.setEnabled( true );
                    menuItemBuildTestPackage.setEnabled( true );
                    menuItemLoadTestSets.setEnabled( true );
                    menuItemExit.setEnabled( true );
                    menuItemTestSets.setEnabled( true );
                    menuItemHowTo.setEnabled( true );
                    menuItemAbout.setEnabled( true );
                    lblStatus.setText( "Done!" );
                    resultPanel.setMouseOverAllowed( true );
                    
                }
                
            }).start();
            
        } else if ( tSet == null ) {
            JOptionPane.showMessageDialog( 
                    this, 
                    "You must select a Test Set!", 
                    "ERROR", JOptionPane.ERROR_MESSAGE );
        } else {
            JOptionPane.showMessageDialog( 
                    this, 
                    "You must have at least one package or source code file to test!", 
                    "ERROR", JOptionPane.ERROR_MESSAGE );
        }
        
    }//GEN-LAST:event_btnRunTestActionPerformed

    private void listTestSetsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTestSetsMousePressed
        
        if ( SwingUtilities.isRightMouseButton( evt ) ) {
            int index = listTestSets.locationToIndex( evt.getPoint() );
            if ( index != -1 ) {
                listTestSets.setSelectedIndex( index );
                listTestSets.requestFocus();
                popupMenu.show( listTestSets, evt.getX(), evt.getY() );
            }
        }
        
    }//GEN-LAST:event_listTestSetsMousePressed

    private void menuItemShowDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemShowDetailsActionPerformed
        
        TestSetDetailsDialog r = new TestSetDetailsDialog( 
                this, 
                true, 
                listTestSets.getSelectedValue() );
        r.setVisible( true );
        
    }//GEN-LAST:event_menuItemShowDetailsActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        System.exit( 0 );
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemTestSetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTestSetsActionPerformed
        
        EditTestSetsDialog etsd = new EditTestSetsDialog( this, true, testSets );
        etsd.setVisible( true );
        
        if ( etsd.getTestSets() != null ) {
            testSets = etsd.getTestSets();
            buildTestSetsModel();
        }
        
    }//GEN-LAST:event_menuItemTestSetsActionPerformed

    private void menuItemHowToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHowToActionPerformed
        
        JOptionPane.showMessageDialog( 
                this, 
                "JJudge uses external tools for compilation and/or execution of\n"
              + "the tested programs.\n\n"
              + "To make it possible, it's necessary to modify the PATH\n"
              + "envinroment variable of your OS to point to the tools that are\n"
              + "necessary or configure the run.bat file.\n\n"
              + "These tools are:\n"
              + "    GCC and G++ for C and C++ code;\n"
              + "    JDK for Java code;\n"
              + "    WinPython for Python code.\n\n"
              + "Note that you will need the appropriate version of the tool\n"
              + "based on the source code that will be compiled and tested.",
                "How to configure and use", 
                JOptionPane.INFORMATION_MESSAGE );
        
    }//GEN-LAST:event_menuItemHowToActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
        JOptionPane.showMessageDialog( 
                this, 
                "JJudge is a Java desktop application that aims to automate\n"
              + "the checking of programs by compiling and executing them by\n"
              + "providing inputs and testing for expected outputs.\n\n"
              + "This tool was developed by Professor Dr. David Buzatto\n"
              + "for application in the disciplines in which he teaches.", 
                "About...", 
                JOptionPane.INFORMATION_MESSAGE );
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void menuItemLoadTestSetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadTestSetsActionPerformed
        loadTestSet();
    }//GEN-LAST:event_menuItemLoadTestSetsActionPerformed

    private void menuItemBuildTestPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBuildTestPackageActionPerformed
        buildTestPackage();
    }//GEN-LAST:event_menuItemBuildTestPackageActionPerformed

    private void btnBuildTestPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuildTestPackageActionPerformed
        buildTestPackage();
    }//GEN-LAST:event_btnBuildTestPackageActionPerformed

    private void btnLoadTestSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadTestSetActionPerformed
        loadTestSet();
    }//GEN-LAST:event_btnLoadTestSetActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPackage;
    private javax.swing.JButton btnBuildTestPackage;
    private javax.swing.JButton btnLoadTestSet;
    private javax.swing.JButton btnRemovePackage;
    private javax.swing.JButton btnRunTest;
    private javax.swing.JCheckBox checkGenerateResultsSpreadsheet;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<File> listPackages;
    private javax.swing.JList<TestSet> listTestSets;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemBuildTestPackage;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemHowTo;
    private javax.swing.JMenuItem menuItemLoadTestSets;
    private javax.swing.JMenuItem menuItemShowDetails;
    private javax.swing.JMenuItem menuItemTestSets;
    private javax.swing.JPanel panelPackages;
    private javax.swing.JPanel panelProcessOutput;
    private javax.swing.JPanel panelResults;
    private javax.swing.JPanel panelTestSets;
    private javax.swing.JPopupMenu popupMenu;
    private br.com.davidbuzatto.jjudge.gui.ResultPanel resultPanel;
    private javax.swing.JScrollPane scrollPackages;
    private javax.swing.JScrollPane scrollProcessOutput;
    private javax.swing.JScrollPane scrollResults;
    private javax.swing.JScrollPane scrollTestSets;
    private javax.swing.JPopupMenu.Separator sepMenuFile01;
    private javax.swing.JPopupMenu.Separator sepMenuFile02;
    private javax.swing.JPopupMenu.Separator sepMenuHelp01;
    private javax.swing.JTextPane textPaneProcessOutput;
    // End of variables declaration//GEN-END:variables
}
