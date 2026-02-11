// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
// We use a hashset to keep track of all the available numbers to give
//  Queue is use to get any available number since searching through hashset is O(N) - thus returns first available number in O(1)
// If a number becomes available, we add it to the queue and hashset

class PhoneDirectory {

    Queue<Integer> q;
    HashSet<Integer> unusedSet;

    public PhoneDirectory(int maxNumbers) {
        this.q = new LinkedList<>();
        this.unusedSet = new HashSet<>();

        for (int i = 0; i < maxNumbers; i++) {
            q.add(i);
            unusedSet.add(i);
        }
    }

    public int get() {
        if (q.isEmpty())
            return -1;

        int num = q.poll();
        unusedSet.remove(num);
        return num;
    }

    public boolean check(int number) {
        return unusedSet.contains(number);
    }

    public void release(int number) {
        if (unusedSet.contains(number))
            return;

        unusedSet.add(number);
        q.add(number);
    }
}
