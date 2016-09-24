public class Solution {
	/*
	public void swap(int a, int b){
		int temp;
		temp = a;
		a = b;
		b = temp;
	}*/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0;
		int p2 = 0;
		
		int[] temp =  new int[nums1.length];
		int t = 0;
		
		while(p1 < m || p2 < n){
			if(p1 >= m){
				temp[t] = nums2[p2];
				p2++;
			}
			else if(p2 >= n){
				temp[t] = nums1[p1];
				p1++;
			}
			else if(nums1[p1] < nums2[p2]){
				temp[t] = nums1[p1];
				p1++;
			}
			else{
				temp[t] = nums2[p2];
				p2++;
			}
			t++;
		}
		if(m == 0){
			for(int i = 0; i < nums2.length; i++) nums1[i] = nums2[i];
		}
		else if(n != 0){
			for(int i = 0; i < nums1.length; i++) nums1[i] = temp[i];
		}
		
    }
}

/*From end to head!!!!
class Solution {
public:
    void merge(int A[], int m, int B[], int n) {
        int i=m-1;
		int j=n-1;
		int k = m+n-1;
		while(i >=0 && j>=0)
		{
			if(A[i] > B[j])
				A[k--] = A[i--];
			else
				A[k--] = B[j--];
		}
		while(j>=0)
			A[k--] = B[j--];
    }
};
*/

/*To be more short
public void merge(int A[], int m, int B[], int n) {
    int i=m-1, j=n-1, k=m+n-1;
    while (i>-1 && j>-1) A[k--]= (A[i]>B[j]) ? A[i--] : B[j--];
    while (j>-1)         A[k--]=B[j--];
}
*/

/*Wow
while(n>0)A[m+n-1]=(m==0||B[n-1]>A[m-1])?B[--n]:A[--m];
*/