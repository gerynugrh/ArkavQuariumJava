public class LinkedList<T> {

    public class Node<T> {
        T value;
        Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public Node() {
            value = null;
            next = null;
        }
    }

    private Node<T> head, tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public T get(int index) {
        Node<T> curr = head;
        int pos = 0;
        while (curr != null) {
            if (pos == index) return curr.value;
            curr = curr.next;
            pos++;
        }
        throw new IndexOutOfBoundsException (index);
    }

    public void remove(T value) {
        Node<T> curr = head;
        if (curr.value == value) {
            if (curr.next == null) tail = null;
            head = curr.next;
            return;
        }
        while (curr.next != null && curr.next.value != value)
            curr = curr.next;
        if (curr.next == null) throw new IndexOutOfBoundsException("No object found");
        curr.next = curr.next.next;
        if (curr.next == null) {
            tail = curr;
        }
    }

    public void add(T value) {
        Node<T> temp = new Node<>(value, null);
        if (head == null) {
            head = temp;
            tail = temp;
            temp = null;
        }
        else {
            tail.next = temp;
            tail = temp;
        }
    }

    public int length() {
        int length = 0;
        Node<T> curr = head;
        while (curr != null) {
            curr = curr.next;
            length++;
        }
        return length;
    }

    public int find(T value) {
        Node<T> curr = head;
        int pos = 0;
        while (curr != null) {
            if (curr.value == value) {
                return pos;
            }
            curr = curr.next;
            pos++;
        }
        throw new IndexOutOfBoundsException("No object found");
    }
}
