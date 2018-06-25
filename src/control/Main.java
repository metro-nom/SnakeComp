package control;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import control.samples.*;
import model.*;
import view.*;

/**
 * Main class. Starts the GUI and possibly pre-loads snake controls if specified by arguments.
 *
 * @author cryingshadow
 */
public class Main
{

	/**
	 * Snake controls to be used when the argument "samples" is being specified for the main method.
	 */
	private static final List<SnakeControl> PRE_DEFINED_SNAKE_CONTROLS =
			Arrays.asList(
					//new GreedySnakeControl(),
					new ODSSnakeControl(),
					//new RandomSnakeControl(),
					new Lakbermit(),
					new Aberkonderbir(),
					new MasterSnakeControl(),
					new Lakbermit2(),
					new LCSS()
					//new RotatingSnakeControl(),
					//new UpLeftSnakeControl()
			);

	/**
	 * @param args Can specify the folder from where to pre-load the snake controls or to use the snake controls stored
	 *             in this class by specifying "samples" as the first argument. If empty, the folder for snake controls
	 *             can be specified from the UI.
	 */
	public static void main(final String[] args)
	{
		final JFrame frame = new JFrame("SnakeTest");
		final JPanel content = new JPanel(new GridBagLayout());
		final JPanel statsAndControls = new JPanel();
		statsAndControls.setLayout(new GridBagLayout());
		final JScrollPane scroll = new JScrollPane(content);
		frame.getContentPane().add(scroll);
		final Settings settings = new Settings();
		final Maze maze = new Maze(settings);
		final Competition competition = new Competition();
		final Snakes snakes = new Snakes();
		final SnakeControls snakeControls = new SnakeControls();
		final CompetitionControl control = new CompetitionControl(settings, maze, snakes, snakeControls, competition);
		final MazeDisplay mazeDisplay = new MazeDisplay(maze, settings);
		final SnakesDisplay snakesDisplay = new SnakesDisplay(settings, snakes);
		final SettingsDisplay settingsDisplay = new SettingsDisplay(settings, competition, control);
		final CompetitionDisplay competitionDisplay =
				new CompetitionDisplay(settings, competition, snakeControls, control);
		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		statsAndControls.add(snakesDisplay, c);
		c.gridy = 1;
		statsAndControls.add(settingsDisplay, c);
		c.gridy = 2;
		statsAndControls.add(competitionDisplay, c);
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHEAST;
		content.add(mazeDisplay, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.0;
		content.add(statsAndControls, c);
		settings.addChangeListener(
				new ChangeListener()
				{

					@Override
					public void stateChanged(final ChangeEvent e)
					{
						frame.validate();
						content.scrollRectToVisible(statsAndControls.getBounds());
					}

				}
		);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		if (args.length > 0)
		{
			if (args[0].equals("samples"))
			{
				control.initSnakes(Main.PRE_DEFINED_SNAKE_CONTROLS);
			} else
			{
				Main.preload(args[0], settings, control);
			}
		}
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Preloads the snake controls in the specified path.
	 *
	 * @param path     The path.
	 * @param settings The settings.
	 * @param control  The competition control.
	 */
	private static void preload(final String path, final Settings settings, final CompetitionControl control)
	{
		settings.setSourceDirectory(Optional.of(new File(path)));
		control.loadSnakes();
	}

}
