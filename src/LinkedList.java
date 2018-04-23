public class LinkedList<T> {

    private Node<T> head, tail;

    LinkedList() {
        head = null;
        tail = null;
    }

    T get(int index) {
        Node<T> curr = head;
        int pos = 0;
        while (curr != null) {
            if (pos == index) return curr.value;
            curr = curr.next;
            pos++;
        }
        throw new IndexOutOfBoundsException(index);
    }

    void remove(T value) {
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

    void add(T value) {
        Node<T> temp = new Node<>(value, null);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            tail.next = temp;
            tail = temp;
        }
    }

    int length() {
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

    public class Node<E> {
        E value;
        Node<E> next;

        Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }
}
