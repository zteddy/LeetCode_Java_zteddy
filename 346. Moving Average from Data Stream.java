public class MovingAverage {
	int s;
	Queue q = new LinkedList();
	double avgnow = 0;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
    	s = size;

    }

    public double next(int val) {              //注意类型
		if(s == 0) return 0;

    	if(q.size() < s){
    		avgnow = avgnow * q.size() + val;
    		q.offer(val);
    		avgnow /= q.size();
    	}
    	else{
    		avgnow *= q.size();
    		avgnow = avgnow -(int)q.poll() + val;
    		q.offer(val);
    		avgnow /= q.size();
    	}

    	return avgnow;

    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */

//TODO None
