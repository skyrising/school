package de.skyrising.info.taxis;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class Taxistand implements Deque<Taxi>
{
    
    private Taxi[] taxis = new Taxi[INITIAL_SIZE];
    private int numTaxis;
    
    private static final int INITIAL_SIZE = 10;

    @Override
    public boolean isEmpty()
    {
	return numTaxis == 0;
    }

    @Override
    public Object[] toArray()
    {
	return Arrays.copyOf(taxis, numTaxis);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a)
    {
	if(a instanceof Taxi[])
	{
	    a = (T[]) Arrays.copyOf(taxis, numTaxis);
	    return a;
	}
	else
	    throw new IllegalArgumentException("Wrong type!");
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean addAll(Collection<? extends Taxi> c)
    {
	for(Taxi t : c)
	    this.add(t);
	return true;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void clear()
    {
	taxis = new Taxi[INITIAL_SIZE];
	numTaxis = 0;
    }

    @Override
    public void addFirst(Taxi e)
    {
	offerFirst(e);
    }

    @Override
    public void addLast(Taxi e)
    {
	offerLast(e);
    }

    @Override
    public boolean offerFirst(Taxi e)
    {
	Taxi[] newTax = new Taxi[numTaxis + ((numTaxis == taxis.length) ? numTaxis / 10 : 0)];
	System.arraycopy(taxis, 0, newTax, 1, numTaxis);
	taxis = newTax;
	taxis[0] = e;
	numTaxis++;
	return true;
    }

    @Override
    public boolean offerLast(Taxi e)
    {
	if(taxis.length == numTaxis)
	{
	   Taxi[] newTax = new Taxi[numTaxis + numTaxis/10];
	   System.arraycopy(taxis, 0, newTax, 0, numTaxis);
	   taxis = newTax;
	}
	taxis[numTaxis++] = e;
	return true;
    }

    @Override
    public Taxi removeFirst()
    {
	if(numTaxis == 0)
	    throw new ArrayIndexOutOfBoundsException(0);
	Taxi t = taxis[0];
	System.arraycopy(taxis, 1, taxis, 0, --numTaxis);
	return t;
    }

    @Override
    public Taxi removeLast()
    {
	if(numTaxis == 0)
	    throw new ArrayIndexOutOfBoundsException(0);
	return taxis[--numTaxis];
    }

    @Override
    public Taxi pollFirst()
    {
	try
	{
	    return removeFirst();
	} catch (ArrayIndexOutOfBoundsException e)
	{
	    return null;
	}
    }

    @Override
    public Taxi pollLast()
    {
	try
	{
	    return removeLast();
	} catch (ArrayIndexOutOfBoundsException e)
	{
	    return null;
	}
    }

    @Override
    public Taxi getFirst()
    {
	if(numTaxis == 0)
	    throw new ArrayIndexOutOfBoundsException(0);
	return taxis[0];
    }

    @Override
    public Taxi getLast()
    {
	if(numTaxis == 0)
	    throw new ArrayIndexOutOfBoundsException(0);
	return taxis[numTaxis-1];
    }

    @Override
    public Taxi peekFirst()
    {
	if(numTaxis == 0)
	    return null;
	return taxis[0];
    }

    @Override
    public Taxi peekLast()
    {
	if(numTaxis == 0)
	    return null;
	return taxis[numTaxis-1];
    }

    @Override
    public boolean removeFirstOccurrence(Object o)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean add(Taxi e)
    {
	addLast(e);
	return true;
    }

    @Override
    public boolean offer(Taxi e)
    {
	return offerLast(e);
    }

    @Override
    public Taxi remove()
    {
	return removeFirst();
    }

    @Override
    public Taxi poll()
    {
	return pollFirst();
    }

    @Override
    public Taxi element()
    {
	if(numTaxis == 0)
	    throw new ArrayIndexOutOfBoundsException(0);
	return taxis[0];
    }

    @Override
    public Taxi peek()
    {
	if(numTaxis == 0)return null;
	return taxis[0];
    }

    @Override
    public void push(Taxi e)
    {
	addFirst(e);
    }

    @Override
    public Taxi pop()
    {
	return removeFirst();
    }

    @Override
    public boolean remove(Object o)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean contains(Object o)
    {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int size()
    {
	return numTaxis;
    }

    @Override
    public Iterator<Taxi> iterator()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Iterator<Taxi> descendingIterator()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("[");
        for(int i = 0; i < numTaxis; i++)
        {
            if(builder.length() != 1)
        	builder.append(", ");
            builder.append(taxis[i]);
        }
        return builder.append(']').toString();
    }
}
