public class MinStack {

	private int min = Integer.MIN_VALUE;
	private List<Integer> stack = new ArrayList<>();
	private List<Integer> heap = new ArrayList<>();
	private int top = -1;
	private int size = 0;

    /** initialize your data structure here. */
    public MinStack() {
    	heap.add(min);

    }

    public void insert(int x){
    	size++;
    	int i = size;
    	int temp;
    	heap.add(x);
    	while(i > 1 && heap.get(i) < heap.get(i/2)){
    		temp = heap.get(i);
    		heap.set(i,heap.get(i/2));
    		heap.set(i/2, temp);
    		i = i/2;
    	}

    }

    private boolean isleaf(int pos) {
        return ((pos > size / 2) && (pos <= size));
    }

    public void heapify(int i){
    	int smallestchild;
    	int temp;
    	while(!isleaf(i)){
    		smallestchild = 2*i;
    		if(smallestchild < size && heap.get(smallestchild) > heap.get(smallestchild+1)){
    			smallestchild++;
    		}
    		if(heap.get(i) <= heap.get(smallestchild)){
    			return;
    		}
    		temp = heap.get(i);
    		heap.set(i,heap.get(smallestchild));
    		heap.set(smallestchild, temp);
    		i = smallestchild;
    	}
    }

    public int extractMin(){
    	int temp;
    	temp = heap.get(1);
    	heap.set(1,heap.get(size));
    	heap.set(size, temp);
    	size--;
    	if(size != 0) heapify(1);
    	return heap.get(size+1);
    }


    public void push(int x) {
        stack.add(x);
        insert(x);
        top++;
    }

    public void pop() {
    	if (top >= 0) {
    		stack.remove(top);
    		extractMin();
    	}
        top--;
    }

    public int top() {
    	return stack.get(top);
    }

    public int getMin() {
    	return 0;

    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

//WA





//AC One stack but used Long
public class MinStack {
    long min = Integer.MAX_VALUE;
    Stack<Long> stack = new Stack<>();
    public MinStack() {

    }

    public void push(int x) {
        stack.push((long)x-min);
        min = Math.min(x, min);
    }

    public void pop() {
        min = Math.max(min - stack.pop(), min);
    }

    public int top() {
        return (int)Math.max(stack.peek() + min, min);
    }

    public int getMin() {
        return (int)min;
    }
}

//AC Two stacks but used int
class MinStack
{
    static class Element
    {
        final int value;
        final int min;
        Element(final int value, final int min)
        {
            this.value = value;
            this.min = min;
        }
    }
    final Stack<Element> stack = new Stack<>();

    public void push(int x) {
        final int min = (stack.empty()) ? x : Math.min(stack.peek().min, x);
        stack.push(new Element(x, min));
    }

    public void pop()
    {
        stack.pop();
    }

    public int top()
    {
        return stack.peek().value;
    }

    public int getMin()
    {
        return stack.peek().min;
    }
}

//AC Add min block into stack
class MinStack {
    int min=Integer.MAX_VALUE;
    Stack<Integer> stack = new Stack<Integer>();
    public void push(int x) {
       // only push the old minimum value when the current
       // minimum value changes after pushing the new value x
        if(x <= min){
            stack.push(min);
            min=x;
        }
        stack.push(x);
    }

    public void pop() {
       // if pop operation could result in the changing of the current minimum value,
       // pop twice and change the current minimum value to the last minimum value.
        if(stack.peek()==min) {
            stack.pop();
            min=stack.peek();
            stack.pop();
        }else{
            stack.pop();
        }
        if(stack.empty()){
            min=Integer.MAX_VALUE;
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}
