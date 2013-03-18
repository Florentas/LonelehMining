package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class PriorityList extends JList implements DragGestureListener, DragSourceListener, DropTargetListener
{
	private static final long serialVersionUID = -9105393242712810720L;
	DefaultListModel listModel = new DefaultListModel();
	DropTarget dropTarget;
	DragSource dragSource;
	
	public PriorityList()
	{
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dragSource = new DragSource();
		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
        dropTarget = new DropTarget(this, this);
        setModel(listModel);
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent e)
	{
		
	}
	
	@Override
	public void dragExit(DropTargetEvent e)
	{
		
	}
	
	@Override
	public void dragOver(DropTargetDragEvent e)
	{
		
	}
	
	@Override
	public void drop(DropTargetDropEvent e)
	{
        Transferable transferable = e.getTransferable();
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            String listItem;
            try
            {
                listItem = (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
            catch(Exception exception)
            {
                e.rejectDrop();
                return;
            }
            e.acceptDrop(DnDConstants.ACTION_MOVE);
            listModel.addElement(listItem);
            e.getDropTargetContext().dropComplete(true);
        }
	}
	
	private void addElement(String listItem)
	{
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent e)
	{
		
	}
	
	@Override
	public void dragDropEnd(DragSourceDropEvent e)
	{
		if (e.getDropSuccess())
		{
            ((DefaultListModel)getModel()).removeElement(getSelectedValue());
        }
	}
	
	@Override
	public void dragEnter(DragSourceDragEvent e)
	{
		
	}
	
	@Override
	public void dragExit(DragSourceEvent e)
	{
		
	}
	
	@Override
	public void dragOver(DragSourceDragEvent e)
	{
		
	}
	
	@Override
	public void dropActionChanged(DragSourceDragEvent e)
	{
		
	}
	
	@Override
	public void dragGestureRecognized(DragGestureEvent e)
	{
		Object val = getSelectedValue();
		
        if (val != null)
        {
            StringSelection stringTransferable = new StringSelection(val.toString());
            dragSource.startDrag(e, DragSource.DefaultMoveDrop, stringTransferable, this);
        }
	}
}
