class MyQueue {

    Stack enqueue = new Stack();
    Stack dequeue = new Stack();

    // Push element x to the back of queue.
    public void push(int x) {
        if(dequeue.empty()) enqueue.push(x);
        else{
            while(!dequeue.empty()){
                enqueue.push(dequeue.pop());
            }
            enqueue.push(x);
        }

    }

    // Removes the element from in front of queue.
    public void pop() {
        if(enqueue.empty()) dequeue.pop();
        else{
            while(!enqueue.empty()){
                dequeue.push(enqueue.pop());
            }
            dequeue.pop();
        }

    }

    // Get the front element.
    public int peek() {
        if(!dequeue.empty()) return (int)dequeue.peek;    //别忘了类型转换
        else{
            while(!enqueue.empty()){
                dequeue.push(enqueue.pop());
            }
            return (int)dequeue.peek();         //别忘了函数后面写()
       }

    }

    // Return whether the queue is empty.
    public boolean empty() {

        return enqueue.empty() && dequeue.empty();

    }
}

//TODO None
