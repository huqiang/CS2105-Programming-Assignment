/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImageFetcherFrame.java
 *
 * Created on Apr 6, 2012, 12:33:23 AM
 */
package edu.sg.nus.cs2105.assignment.imageFetcher.view;

import edu.sg.nus.cs2105.assignment.imageFetcher.controller.ImageFetcher;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import javax.swing.JFileChooser;

/**
 *
 * @author liyilin
 */
public class ImageFetcherFrame extends javax.swing.JFrame {

    /** Creates new form ImageFetcherFrame */
    public ImageFetcherFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        urlText = new javax.swing.JTextField();
        downloadButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        chooseButton = new javax.swing.JButton();
        pathText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        urlText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlTextActionPerformed(evt);
            }
        });
        urlText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                urlTextFocusLost(evt);
            }
        });

        downloadButton.setText("Download");
        downloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadButtonMouseClicked(evt);
            }
        });

        jLabel1.setText("URL:");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        chooseButton.setText("choose");
        chooseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseButtonMouseClicked(evt);
            }
        });

        pathText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathTextActionPerformed(evt);
            }
        });

        jLabel2.setText("PATH:");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pathText, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chooseButton)
                                .addGap(9, 9, 9))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(urlText, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGap(124, 124, 124)
                            .addComponent(downloadButton)
                            .addGap(106, 106, 106)))
                    .addContainerGap(57, Short.MAX_VALUE)))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 117, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(urlText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chooseButton)
                        .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(downloadButton)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urlTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urlTextActionPerformed

    private void pathTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathTextActionPerformed

    private void urlTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_urlTextFocusLost
        url = urlText.getText();
    }//GEN-LAST:event_urlTextFocusLost

    private void chooseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseButtonMouseClicked
        ActionEvent e = null;
        getPath(e);
    }//GEN-LAST:event_chooseButtonMouseClicked

    private void downloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downloadButtonMouseClicked
        InputStream in = null;
        try {
            //send url and store directroy to controller
            URL webPage = new URL(url);
            ImageFetcher iF = new ImageFetcher();
            //and a '/' to combine the path with file 
            iF.setClientPath(FilePath + '/');
            in = iF.loadURL(webPage);
            iF.setUrls(iF.getImageUrls(in, webPage.getHost()));
            Vector<URL> tempURLs = iF.getUrls();
            for (int i = 0; i < tempURLs.size(); i++) {
                iF.getImages(tempURLs.get(i));
            }
            System.out.println("Total " + tempURLs.size() +
                    " images." + "\nDone downloading all images.");


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_downloadButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField pathText;
    private javax.swing.JTextField urlText;
    // End of variables declaration//GEN-END:variables
    private String url = null;
    private String FilePath = null;
    private JFileChooser chooser;

    public void getPath(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Path");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        try {
            if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                FilePath = chooser.getSelectedFile().getCanonicalPath();
                pathText.setText(FilePath);

            } else {
                System.out.println("No Selection ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
