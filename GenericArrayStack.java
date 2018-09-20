
public class GenericArrayStack<E> implements Stack<E> {
   
    private E[] elems;
    private int top;
   // Constructor

    @SuppressWarnings("unchecked")

    public GenericArrayStack(int capacity ) {
        elems = (E[]) new Object[ capacity ];
        top = 0;

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        for(int index = 0; index < elems.length; index++){
            if(elems[index] != null){
                return false;
            }
        }
        return true;
    }

    public void push( E elem ) {
        if(top < elems.length){
            elems[top] = elem;
            top++;
        }
        

    }
    public E pop() {
       E topElement = elems[top-1];

       elems[top-1] = null;
       top--;
       return topElement;
        

    }

    public E peek() {
        return elems[top-1];
        

    }
}
