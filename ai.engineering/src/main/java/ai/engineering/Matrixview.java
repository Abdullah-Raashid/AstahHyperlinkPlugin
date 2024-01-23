package ai.engineering;

import java.util.*;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.change_vision.jude.api.inf.model.*;

import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.project.ProjectEvent;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import javax.swing.*;

public class Matrixview extends JPanel implements IPluginExtraTabView, ProjectEventListener {
    private JTable summaryTable;
    private String[] headerString = {" ","1","2","3","4","5","6","7","8","9","10"};

    public Matrixview(){
        // making the list of all elements that have hyperlinks
        List<IHyperlinkOwner> hyperlinks = ElementPicker.getAllHyperlinksOwner();
        Object[][] tableData = new Object[11][11];
        
        int size = hyperlinks.size();

        size = 20;

        tableData = new Object[size][size];
        headerString = new String[size+1];

        int index = 0;
        
        for (int i = 0; i < hyperlinks.size(); i++) {
            List<IEntity> related = ElementPicker.getRelatedEntities(hyperlinks.get(i));

            // set the header and first column
            if(!related.isEmpty() & index < size){
                INamedElement r1 = (INamedElement)hyperlinks.get(i);
                headerString[index+1] = r1.getName();
                tableData[index][0] = r1.getName();
                index = index+1;
            }
        }

        int index2 = 0;

        //getting and setting the elements
        for (int i = 0; i < hyperlinks.size(); i++) {
            List<IEntity> related = ElementPicker.getRelatedEntities(hyperlinks.get(i));
            List<String> display = new ArrayList<String>();
           
            // getting names of related elements
            for (int j = 0; j < related.size(); j++) {
                INamedElement r2 = (INamedElement)related.get(j);
                display.add(r2.getName());
            }

            if (!display.isEmpty() && index2 < size-1) {
                for (int j = 0; j < display.size(); j++) {
                    for (int k = 0; k < tableData.length; k++) {
                        if (display.get(j).equals(tableData[k][0])) {
                            System.out.println("Comparing");
                            tableData[k][index2+1] = display.get(j);
                        }   
                    }
                }
                index2++;
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(tableData, headerString);
        summaryTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(summaryTable);
        scrollPane.setPreferredSize(new Dimension(800, 800));

        add(scrollPane);
    }

    @Override
    public void projectChanged(ProjectEvent e) {}

    @Override
    public void projectClosed(ProjectEvent e) {}

    @Override
    public void projectOpened(ProjectEvent e) {}

    @Override
    public void addSelectionListener(ISelectionListener listener) {}

    @Override
    public String getTitle() {return "Pipeline Monitoring Summary";}

    @Override
    public Component getComponent() {return this;}

    @Override
    public String getDescription() {return "Pipeline Monitoring Summary Class";}

    public void activated() {}

    public void deactivated() {}

}
