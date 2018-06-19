package control.samples;

import control.*;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ODSSnakeControl implements SnakeControl
{

	@Override
	public String getName()
	{
		return "ODSSnake";
	}

	@Override
	public Direction nextDirection(Maze maze, int xPos, int yPos)
	{
		final List<Position> food = new ArrayList<Position>(maze.getFood());
		final Position curPos = new Position(xPos, yPos);
		food.sort(
				new Comparator<Position>()
				{
					@Override
					public int compare(final Position o1, final Position o2)
					{
						return
								ODSSnakeControl.manhattanDistance(o1, curPos)
										- ODSSnakeControl.manhattanDistance(o2, curPos);
					}
				}
		);
		final Position foodPos = food.get(0);
		final int foodX = foodPos.getX();
		final int foodY = foodPos.getY();
		if (foodX > xPos)
		{
			return this.decide(maze, xPos, yPos);
		}
		if (foodX < xPos)
		{
			return this.decide(maze, xPos, yPos);
		}
		if (foodY > yPos)
		{
			return this.decide(maze, xPos, yPos);
		}
		return this.decide(maze, xPos, yPos);
	}

	private static int manhattanDistance(final Position p1, final Position p2)
	{
		return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
	}

	private Direction decide(Maze maze, int xPos, int yPos)
	{
		final List<Position> surrounding =
				maze
						.getSurroundingPositions(xPos, yPos)
						.stream()
						.filter(pos -> !maze.getField(pos).getType().isObstacle())
						.collect(Collectors.toList());
		return new Position(xPos, yPos).computeDirection(surrounding.get(0));
	}
}

