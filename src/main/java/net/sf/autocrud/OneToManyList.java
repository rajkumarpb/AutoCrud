// $Id: OneToManyList.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/OneToManyList.java,v $
package net.sf.autocrud;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Represents a List of ActiveRecords that are linked to the current ActiveRecord
 * using a one-to-many relation.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class OneToManyList<R extends ActiveRecord> implements List<ActiveRecord> {

  private List<ActiveRecord> collection;

  public boolean add(ActiveRecord e) {
    return collection.add(e);
  }

  public void add(int index, ActiveRecord element) {
    collection.add(index, element);
  }

  public boolean addAll(Collection<? extends ActiveRecord> c) {
    return collection.addAll(c);
  }

  public boolean addAll(int index, Collection<? extends ActiveRecord> c) {
    return collection.addAll(index, c);
  }

  public void clear() {
    collection.clear();
  }

  public boolean contains(Object o) {
    return collection.contains(o);
  }

  public boolean containsAll(Collection<?> c) {
    return collection.containsAll(c);
  }

  public int indexOf(Object o) {
    return collection.indexOf(o);
  }

  public boolean isEmpty() {
    return collection.isEmpty();
  }

  public Iterator<ActiveRecord> iterator() {
    return collection.iterator();
  }

  public int lastIndexOf(Object o) {
    return collection.lastIndexOf(o);
  }

  public ListIterator<ActiveRecord> listIterator() {
    return collection.listIterator();
  }

  public ListIterator<ActiveRecord> listIterator(int index) {
    return collection.listIterator(index);
  }

  public boolean remove(Object o) {
    return collection.remove(o);
  }

  public ActiveRecord remove(int index) {
    return collection.remove(index);
  }

  public boolean removeAll(Collection<?> c) {
    return collection.removeAll(c);
  }

  public boolean retainAll(Collection<?> c) {
    return collection.retainAll(c);
  }

  public ActiveRecord set(int index, ActiveRecord element) {
    return collection.set(index, element);
  }

  public int size() {
    return collection.size();
  }

  public List<ActiveRecord> subList(int fromIndex, int toIndex) {
    return collection.subList(fromIndex, toIndex);
  }

  public Object[] toArray() {
    return collection.toArray();
  }

  public <T> T[] toArray(T[] a) {
    return collection.toArray(a);
  }

  public ActiveRecord get(int index) {
    return collection.get(index);
  }

}
