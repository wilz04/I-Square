public class List {
  
  public Cell top;
  
  public List() {
    top = null;
  }

  public int size() {
    int count = 0;
    Cell temp = top;
    while (temp != null) {
      temp = temp.next;
      count++;
    }
    return count;
  }

  public void insert(Cell c) {
    if (top == null) {
      top = c;
    } else {
      Cell temp = top;
      while (temp.next != null) {
        temp = temp.next;
      }
      temp.next = c;
    }
  }

  public void insert(Cell c, int n) {
    int i;
    Cell temp_a = top;
    if (n == 1) {
      c.next = temp_a;
      top = c;
    } else {
      for (i = 2; i < n; i++) {
        temp_a = temp_a.next;
      }
      Cell temp_b = temp_a.next;
      temp_a.next = c;
      c.next = temp_b;
    }
  }

  public void delete() {
    if (top != null) {
      top = top.next;
    }
  }

  public void delete(int n) {
    int i;
    Cell temp = top;
    if (n == 1) {
      top = top.next;
    } else {
      for (i = 2; i < n; i++) {
        temp = temp.next;
      }
      temp.next = temp.next.next;
    }
  }

  public void toEmpty() {
    top = null;
  }

  public boolean isEmpty() {
    return top == null;
  }

  public void show() {
    Cell temp = top;
    while (temp != null) {
      System.out.println(temp.name);
      temp = temp.next;
    }
  }

  public String[][] toCellArray() {
    int i = 0;
    String data[][] = new String[size()][3];
    Cell temp = top;
    while (temp != null) {
      data[i][0] = temp.name;
      data[i][1] = temp.value;
      data[i][2] = temp.caption;
      temp = temp.next;
      i++;
    }
    return data;
  }

  public String nameToCaption(String name) {
    String caption = "";
    int i = 0;
    Cell temp = top;
    while (temp != null) {
      if (name.equals(temp.name)) {
        caption = temp.caption;
      }
      temp = temp.next;
    }
    return caption;
  }

  public void find(String word) {
    Cell temp = top;
    while (temp != null) {
      if (temp.caption.equals(word)) {
        break;
      } else {
        temp = temp.next;
      }
    }
    temp.requestFocus();
  }

  public void replace(String caption, String newValue) {
    Cell temp = top;
    while (temp != null) {
      if (temp.caption.equals(caption)) {
        temp.value = newValue;
        break;
      }
      temp = temp.next;
      temp.requestFocus();
    }
  }
  
}