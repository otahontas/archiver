use std::cmp::Ordering;

// Custom List implementation (similar to ArrayList)
#[derive(Clone)]
pub struct List<T: Clone> {
    values: Vec<T>,
}

impl<T: Clone> List<T> {
    pub fn new() -> Self {
        List {
            values: Vec::with_capacity(8),
        }
    }

    pub fn add(&mut self, value: T) {
        self.values.push(value);
    }

    pub fn get(&self, index: usize) -> T {
        self.values[index].clone()
    }

    pub fn size(&self) -> usize {
        self.values.len()
    }

    pub fn extend(&mut self, other: &List<T>) {
        for i in 0..other.size() {
            self.add(other.get(i));
        }
    }

    pub fn remove_last(&mut self, times: usize) {
        for _ in 0..times {
            self.values.pop();
        }
    }
}

// Pair implementation
#[derive(Clone)]
pub struct Pair<K, V> {
    key: K,
    value: V,
}

impl<K, V> Pair<K, V> {
    pub fn new(key: K, value: V) -> Self {
        Pair { key, value }
    }

    pub fn get_key(&self) -> &K {
        &self.key
    }

    pub fn get_value(&self) -> &V {
        &self.value
    }

    pub fn set_value(&mut self, value: V) {
        self.value = value;
    }
}

// HashMap implementation
pub struct HashMap<K: Clone + PartialEq + std::hash::Hash, V: Clone> {
    values: Vec<Option<Vec<Pair<K, V>>>>,
    size: usize,
}

impl<K: Clone + PartialEq + std::hash::Hash, V: Clone> HashMap<K, V> {
    pub fn new() -> Self {
        HashMap {
            values: vec![None; 32],
            size: 0,
        }
    }

    pub fn contains(&self, key: &K) -> bool {
        self.get(key).is_some()
    }

    pub fn get(&self, key: &K) -> Option<V> {
        let hash = self.get_hash(key);
        if let Some(ref list) = self.values[hash] {
            for pair in list {
                if pair.get_key() == key {
                    return Some(pair.get_value().clone());
                }
            }
        }
        None
    }

    pub fn put(&mut self, key: K, value: V) {
        let hash = self.get_hash(&key);

        if self.values[hash].is_none() {
            self.values[hash] = Some(Vec::new());
        }

        if let Some(ref mut list) = self.values[hash] {
            for pair in list.iter_mut() {
                if pair.get_key() == &key {
                    pair.set_value(value);
                    return;
                }
            }
            list.push(Pair::new(key, value));
            self.size += 1;
        }

        if (self.size as f64 / self.values.len() as f64) > 0.75 {
            self.increase_size();
        }
    }

    fn get_hash(&self, key: &K) -> usize {
        use std::collections::hash_map::DefaultHasher;
        use std::hash::Hasher;
        let mut hasher = DefaultHasher::new();
        key.hash(&mut hasher);
        (hasher.finish() as usize) % self.values.len()
    }

    fn increase_size(&mut self) {
        let new_size = self.values.len() * 2;
        let mut new_values = vec![None; new_size];

        for bucket in &self.values {
            if let Some(ref list) = bucket {
                for pair in list {
                    let hash = {
                        use std::collections::hash_map::DefaultHasher;
                        use std::hash::Hasher;
                        let mut hasher = DefaultHasher::new();
                        pair.get_key().hash(&mut hasher);
                        (hasher.finish() as usize) % new_size
                    };

                    if new_values[hash].is_none() {
                        new_values[hash] = Some(Vec::new());
                    }
                    if let Some(ref mut new_list) = new_values[hash] {
                        new_list.push(Pair::new(pair.get_key().clone(), pair.get_value().clone()));
                    }
                }
            }
        }
        self.values = new_values;
    }
}

// Huffman Node
#[derive(Clone)]
pub struct Node {
    frequency: i32,
    value: i32,
    left_child: Option<Box<Node>>,
    right_child: Option<Box<Node>>,
}

impl Node {
    pub fn new(frequency: i32, value: i32) -> Self {
        Node {
            frequency,
            value,
            left_child: None,
            right_child: None,
        }
    }

    pub fn get_frequency(&self) -> i32 {
        self.frequency
    }

    pub fn get_value(&self) -> i32 {
        self.value
    }

    pub fn set_left_child(&mut self, child: Node) {
        self.left_child = Some(Box::new(child));
    }

    pub fn set_right_child(&mut self, child: Node) {
        self.right_child = Some(Box::new(child));
    }

    pub fn get_left_child(&self) -> Option<&Node> {
        self.left_child.as_deref()
    }

    pub fn get_right_child(&self) -> Option<&Node> {
        self.right_child.as_deref()
    }
}

impl PartialEq for Node {
    fn eq(&self, other: &Self) -> bool {
        self.frequency == other.frequency && self.value == other.value
    }
}

impl Eq for Node {}

impl PartialOrd for Node {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Ord for Node {
    fn cmp(&self, other: &Self) -> Ordering {
        if self.frequency == other.frequency {
            self.value.cmp(&other.value)
        } else {
            self.frequency.cmp(&other.frequency)
        }
    }
}

// Minimum Priority Queue
pub struct MinimumPriorityQueue {
    heap: Vec<Node>,
}

impl MinimumPriorityQueue {
    pub fn new() -> Self {
        MinimumPriorityQueue {
            heap: Vec::with_capacity(256),
        }
    }

    pub fn get_size(&self) -> usize {
        self.heap.len()
    }

    pub fn is_empty(&self) -> bool {
        self.heap.is_empty()
    }

    pub fn insert(&mut self, node: Node) {
        self.heap.push(node);
        let mut i = self.heap.len() - 1;
        while i > 0 && self.heap[i / 2] > self.heap[i] {
            self.heap.swap(i, i / 2);
            i /= 2;
        }
    }

    pub fn pop_minimum_element(&mut self) -> Node {
        let min = self.heap[0].clone();
        let last = self.heap.pop().unwrap();
        if !self.heap.is_empty() {
            self.heap[0] = last;
            self.heapify(0);
        }
        min
    }

    fn heapify(&mut self, index: usize) {
        let left = 2 * index + 1;
        let right = 2 * index + 2;
        let mut smallest = index;

        if left < self.heap.len() && self.heap[left] < self.heap[index] {
            smallest = left;
        }
        if right < self.heap.len() && self.heap[right] < self.heap[smallest] {
            smallest = right;
        }
        if smallest != index {
            self.heap.swap(index, smallest);
            self.heapify(smallest);
        }
    }
}

// Huffman Tree
pub struct HuffmanTree {
    root: Option<Node>,
    tree_pre_order: List<bool>,
    values: List<u8>,
}

impl HuffmanTree {
    pub fn new() -> Self {
        HuffmanTree {
            root: None,
            tree_pre_order: List::new(),
            values: List::new(),
        }
    }

    pub fn form_huffman_tree_for_encoding(&mut self, priority_queue: &mut MinimumPriorityQueue) {
        // Handle edge case: if only one unique value, create a parent node
        if priority_queue.get_size() == 1 {
            let single_node = priority_queue.pop_minimum_element();
            let mut parent = Node::new(single_node.get_frequency(), 128);
            parent.set_left_child(single_node);
            // Create a dummy right child to ensure proper tree structure
            parent.set_right_child(Node::new(0, 128));
            self.root = Some(parent);
            return;
        }

        while priority_queue.get_size() > 1 {
            let x = priority_queue.pop_minimum_element();
            let y = priority_queue.pop_minimum_element();
            let mut z = Node::new(x.get_frequency() + y.get_frequency(), 128);
            z.set_left_child(x);
            z.set_right_child(y);
            priority_queue.insert(z);
        }
        self.root = Some(priority_queue.pop_minimum_element());
    }

    pub fn form_huffman_tree_for_decoding(&mut self, preorder: &List<bool>, node_values: &[u8]) {
        if preorder.size() == 0 {
            return;
        }

        let mut values_index = 0;
        self.root =
            Some(self.build_tree_recursive(preorder, node_values, &mut 0, &mut values_index));
    }

    fn build_tree_recursive(
        &self,
        preorder: &List<bool>,
        node_values: &[u8],
        index: &mut usize,
        values_index: &mut usize,
    ) -> Node {
        if *index >= preorder.size() {
            return Node::new(128, 128);
        }

        let is_leaf = preorder.get(*index);
        *index += 1;

        if is_leaf {
            let value = node_values[*values_index] as i32;
            *values_index += 1;
            Node::new(128, value)
        } else {
            let mut node = Node::new(128, 128);
            node.set_left_child(self.build_tree_recursive(
                preorder,
                node_values,
                index,
                values_index,
            ));
            node.set_right_child(self.build_tree_recursive(
                preorder,
                node_values,
                index,
                values_index,
            ));
            node
        }
    }

    pub fn get_root(&self) -> &Node {
        self.root.as_ref().unwrap()
    }

    pub fn get_tree_size(&self) -> i16 {
        self.tree_pre_order.size() as i16
    }

    pub fn set_tree_pre_order(&mut self, order: List<bool>) {
        self.tree_pre_order = order;
    }

    pub fn get_tree_pre_order(&self) -> &List<bool> {
        &self.tree_pre_order
    }

    pub fn set_values(&mut self, values: List<u8>) {
        self.values = values;
    }

    pub fn get_values(&self) -> Vec<u8> {
        let mut result = Vec::with_capacity(self.values.size());
        for i in 0..self.values.size() {
            result.push(self.values.get(i));
        }
        result
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_list_add_and_get() {
        let mut list = List::new();
        list.add(1);
        list.add(2);
        list.add(3);
        assert_eq!(list.size(), 3);
        assert_eq!(list.get(0), 1);
        assert_eq!(list.get(1), 2);
        assert_eq!(list.get(2), 3);
    }

    #[test]
    fn test_list_extend() {
        let mut list1 = List::new();
        list1.add(1);
        list1.add(2);

        let mut list2 = List::new();
        list2.add(3);
        list2.add(4);

        list1.extend(&list2);
        assert_eq!(list1.size(), 4);
        assert_eq!(list1.get(2), 3);
        assert_eq!(list1.get(3), 4);
    }

    #[test]
    fn test_hashmap_put_and_get() {
        let mut map = HashMap::new();
        map.put("key1", 100);
        map.put("key2", 200);

        assert_eq!(map.get(&"key1"), Some(100));
        assert_eq!(map.get(&"key2"), Some(200));
        assert_eq!(map.get(&"key3"), None);
    }

    #[test]
    fn test_hashmap_contains() {
        let mut map = HashMap::new();
        map.put("key1", 100);
        assert!(map.contains(&"key1"));
        assert!(!map.contains(&"key2"));
    }

    #[test]
    fn test_priority_queue() {
        let mut pq = MinimumPriorityQueue::new();
        pq.insert(Node::new(5, 1));
        pq.insert(Node::new(3, 2));
        pq.insert(Node::new(7, 3));

        let min = pq.pop_minimum_element();
        assert_eq!(min.get_frequency(), 3);
    }

    #[test]
    fn test_node_comparison() {
        let node1 = Node::new(5, 1);
        let node2 = Node::new(3, 2);
        assert!(node2 < node1);
    }
}
