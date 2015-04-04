package com.mrcrayfish.modelcreator.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.element.Face;

public class UVPanel extends JPanel implements IValueUpdater
{
	private static final long serialVersionUID = 1L;

	private ElementManager manager;
	private JButton btnPlusX;
	private JButton btnPlusY;
	private JTextField xStartField;
	private JTextField yStartField;
	private JButton btnNegX;
	private JButton btnNegY;
	
	private JButton btnPlusXEnd;
	private JButton btnPlusYEnd;
	private JTextField xEndField;
	private JTextField yEndField;
	private JButton btnNegXEnd;
	private JButton btnNegYEnd;

	private DecimalFormat df = new DecimalFormat("#.#");

	public UVPanel(ElementManager manager)
	{
		this.manager = manager;
		setLayout(new GridLayout(3, 3, 4, 4));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "UV"));
		setMaximumSize(new Dimension(186, 124));
		initComponents();
		initProperties();
		addComponents();
	}

	public void initComponents()
	{
		btnPlusX = new JButton("+");
		btnPlusY = new JButton("+");
		xStartField = new JTextField();
		yStartField = new JTextField();
		btnNegX = new JButton("-");
		btnNegY = new JButton("-");
		
		btnPlusXEnd = new JButton("+");
		btnPlusYEnd = new JButton("+");
		xEndField = new JTextField();
		yEndField = new JTextField();
		btnNegXEnd = new JButton("-");
		btnNegYEnd = new JButton("-");
	}

	public void initProperties()
	{
		Font defaultFont = new Font("SansSerif", Font.BOLD, 20);
		xStartField.setSize(new Dimension(62, 30));
		xStartField.setFont(defaultFont);
		xStartField.setEditable(false);
		xStartField.setHorizontalAlignment(JTextField.CENTER);

		yStartField.setSize(new Dimension(62, 30));
		yStartField.setFont(defaultFont);
		yStartField.setEditable(false);
		yStartField.setHorizontalAlignment(JTextField.CENTER);
		
		xEndField.setSize(new Dimension(62, 30));
		xEndField.setFont(defaultFont);
		xEndField.setEditable(false);
		xEndField.setHorizontalAlignment(JTextField.CENTER);

		yEndField.setSize(new Dimension(62, 30));
		yEndField.setFont(defaultFont);
		yEndField.setEditable(false);
		yEndField.setHorizontalAlignment(JTextField.CENTER);

		btnPlusX.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureX(0.1);
				}
				else
				{
					face.addTextureX(1.0);
				}
				xStartField.setText(df.format(face.getStartU()));
			}
		});
		btnPlusX.setSize(new Dimension(62, 30));
		btnPlusX.setFont(defaultFont);

		btnPlusY.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureY(0.1);
				}
				else
				{
					face.addTextureY(1.0);
				}
				yStartField.setText(df.format(face.getStartV()));
			}
		});
		btnPlusY.setPreferredSize(new Dimension(62, 30));
		btnPlusY.setFont(defaultFont);

		btnNegX.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureX(-0.1);
				}
				else
				{
					face.addTextureX(-1.0);
				}
				xStartField.setText(df.format(face.getStartU()));
			}
		});
		btnNegX.setSize(new Dimension(62, 30));
		btnNegX.setFont(defaultFont);

		btnNegY.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureY(-0.1);
				}
				else
				{
					face.addTextureY(-1.0);
				}
				yStartField.setText(df.format(face.getStartV()));
			}
		});
		btnNegY.setSize(new Dimension(62, 30));
		btnNegY.setFont(defaultFont);
		
		btnPlusXEnd.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureXEnd(0.1);
				}
				else
				{
					face.addTextureXEnd(1.0);
				}
				xEndField.setText(df.format(face.getEndU()));
			}
		});
		btnPlusXEnd.setSize(new Dimension(62, 30));
		btnPlusXEnd.setFont(defaultFont);

		btnPlusYEnd.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureYEnd(0.1);
				}
				else
				{
					face.addTextureYEnd(1.0);
				}
				yEndField.setText(df.format(face.getEndV()));
			}
		});
		btnPlusYEnd.setPreferredSize(new Dimension(62, 30));
		btnPlusYEnd.setFont(defaultFont);

		btnNegXEnd.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureXEnd(-0.1);
				}
				else
				{
					face.addTextureXEnd(-1.0);
				}
				xEndField.setText(df.format(face.getEndU()));
			}
		});
		btnNegXEnd.setSize(new Dimension(62, 30));
		btnNegXEnd.setFont(defaultFont);

		btnNegYEnd.addActionListener(e ->
		{
			if (manager.getSelectedCuboid() != null)
			{
				Element cube = manager.getSelectedCuboid();
				Face face = cube.getSelectedFace();
				if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 1)
				{
					face.addTextureYEnd(-0.1);
				}
				else
				{
					face.addTextureYEnd(-1.0);
				}
				yEndField.setText(df.format(face.getEndV()));
			}
		});
		btnNegYEnd.setSize(new Dimension(62, 30));
		btnNegYEnd.setFont(defaultFont);
	}

	public void addComponents()
	{
		add(btnPlusX);
		add(btnPlusY);
		add(btnPlusXEnd);
		add(btnPlusYEnd);
		add(xStartField);
		add(yStartField);
		add(xEndField);
		add(yEndField);
		add(btnNegX);
		add(btnNegY);
		add(btnNegXEnd);
		add(btnNegYEnd);
	}

	@Override
	public void updateValues(Element cube)
	{
		if (cube != null)
		{
			xStartField.setText(df.format(cube.getSelectedFace().getStartU()));
			yStartField.setText(df.format(cube.getSelectedFace().getStartV()));
			xEndField.setText(df.format(cube.getSelectedFace().getEndU()));
			yEndField.setText(df.format(cube.getSelectedFace().getEndV()));
		}
		else
		{
			xStartField.setText("");
			yStartField.setText("");
			xEndField.setText("");
			yEndField.setText("");
		}
	}
}
