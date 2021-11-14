package tCalc;

import java.util.Stack;

public class Pile extends Stack<Double>{
	public Pile ()
	{
		super();
	}
	public void drop()
	{
		this.pop();
	}
}