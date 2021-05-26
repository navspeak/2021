package nav.linkedList;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    int maxSize;
    Map<String, Node> map = new HashMap<>();
    Node head = null, tail = null;
    int size = 0;

    public LRUCache(int maxSize) {
        this.maxSize = maxSize > 1 ? maxSize : 1;
    }

    public void insertKeyValuePair(String key, int value) {
        Node node = null;
        if (head == null) {
            node = new Node(key,value);
            map.put(key, node);
            head = node;
            tail = node;
            size = 1;
            return;
        }
        if (map.containsKey(key) == true){
            node = map.get(key);
            node.value = value;
            if (size == 1) return;
            node.prev.next = node.next;
            node.prev = tail;
            tail.next = node;
            node.next = null;
            tail = node;

            return;
        }
        node = new Node(key, value);
        if (maxSize == 1){
            map.remove(head.key);
            map.put(key, node);
            head = node;
            tail = node;
            return;
        }

        if (size == maxSize) {
            map.remove(head.key);
            head = head.next;
            size--;
        }
        map.put(key, node);
        tail.next = node;
        node.prev = tail;
        tail = node;
        size++;

    }

    public LRUResult getValueFromKey(String key) {
        if (map.containsKey(key)) {
            //  X ->  N -> Y -> P -> Q -> R ->T ->NULL
            //    <-    <-
            Node node = map.get(key);
            if (size > 1) {
                if(node == head){
                    head = node.next;
                } else {
                    node.prev.next = node.next;
                }
                node.prev = tail;
                tail.next = node;
                node.next = null;
                tail = node;
            }
            return new LRUResult(true, node.value);
        }
        return new LRUResult(false, Integer.MIN_VALUE);
    }

    public String getMostRecentKey() {
        return tail == null? null : tail.key;
    }

    static class LRUResult {
        boolean found;
        int value;

        public LRUResult(boolean found, int value) {
            this.found = found;
            this.value = value;
        }
    }

    static class Node {
        String key;
        int value;
        Node prev;
        Node next;
        Node(String key, int val){
            this.key = key;
            this.value = val;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        cache.insertKeyValuePair("b",2);
        cache.insertKeyValuePair("b",1);
        cache.insertKeyValuePair("c",3);
        System.out.println(cache.getMostRecentKey());
        System.out.println(cache.getValueFromKey("a").value);
        System.out.println(cache.getMostRecentKey());
        cache.insertKeyValuePair("d",4);
        System.out.println(cache.getValueFromKey("b").value);
        cache.insertKeyValuePair("a",5);
        System.out.println(cache.getValueFromKey("a").value);

        cache = new LRUCache(1);
        System.out.println(cache.getValueFromKey("a").value);
        cache.insertKeyValuePair("a",1);
        System.out.println(cache.getValueFromKey("a").value);
        cache.insertKeyValuePair("a",9001);
        System.out.println(cache.getValueFromKey("a").value);
        cache.insertKeyValuePair("b",2);
        System.out.println(cache.getValueFromKey("b").value);


    }
}

