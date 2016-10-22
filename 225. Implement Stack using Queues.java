class MyStack {

    int size = 0;
    int t;
    Queue q = new LinkedList();

    // Push element x onto stack.
    public void push(int x) {
        q.offer(x);
        size++;
        t = x;
    }

    // Removes the element on top of the stack.
    public void pop() {
        for(int i = 1; i <= size-1; i++){
            push((int)q.poll());、
            size--;
        }
        q.poll();
        size--;

        //if(!q.isEmpty())
            //t = (int)q.peek();

    }

    // Get the top element.
    public int top() {
        return t;

    }

    // Return whether the stack is empty.
    public boolean empty() {
        return q.isEmpty();
            //Queue的isEmpty()和Stack的empty()名字不同
    }
}

//TODO None



