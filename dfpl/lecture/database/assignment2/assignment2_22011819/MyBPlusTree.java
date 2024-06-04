package org.dfpl.lecture.database.assignment2.assignment2_22011819;

import java.util.*;

@SuppressWarnings("unused")
public class MyBPlusTree implements NavigableSet<Integer> {

	// Data Abstraction은 예시일 뿐 자유롭게 B+ Tree의 범주 안에서 어느정도 수정가능
	private MyBPlusTreeNode root;
	private LinkedList<MyBPlusTreeNode> leafList;

	// key property를 확인하기 위해 상수로 선언함
	private int MAX_KEY;	// max key property
	private int MIN_KEY;	// min key property

	public MyBPlusTree(int m) {
		// root 노드를 생성하고 초기화
		root = new MyBPlusTreeNode();

		root.setParent(null); 					// root의 부모는 없으므로 null을 설정
		root.setKeyList(new ArrayList<>()); 	// 빈 키 리스트로 초기화
		root.setChildren(new ArrayList<>()); 	// 빈 자식 리스트로 초기화
		root.setLeaf(true);			// 생성된 순간은 root == leaf

		// leafList를 LinkedList의 새 인스턴스로 초기화
		leafList = new LinkedList<>();

		// m의 값에 맞게 min key, max key property 설정
		MAX_KEY = m - 1;
		MIN_KEY =(int)Math.ceil(m / 2.0) - 1;		// 올림
	}

	/**
	 * 과제 Assignment4를 위한 메소드:
	 *
	 * key로 검색하면 root부터 시작하여, key를 포함할 수 있는 leaf node를 찾고 key가 실제로 존재하면 해당 Node를
	 * 반환하고, 그렇지 않다면 null을 반환한다. 중간과정을 System.out.println(String) 으로 출력해야 함. 6 way
	 * B+ tree에서 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21 이 순서대로
	 * add되었다고 했을 때,
	 *
	 * 예: getNode(11)을 수행하였을 때
	 * > larger than or equal to 10
	 * > less than 13
	 * > 11 found
	 * 위의 3 문장을
	 * 콘솔에 출력하고 11을 포함한 MyBPlusTreeNode를 반환함
	 *
	 * 예: getNode(22)를 수행하였을 때
	 * > larger than or equal to 10
	 * > larger than or equal to 19
	 * > 22 not found
	 * 위의 3
	 * 문장을 콘솔에 출력하고 null을 반환함.
	 *
	 * @param key
	 * @return
	 */

	/**
	 * todo: getNode 메서드 내부에서 트리를 타고 내려가면서 문장을 출력해야됨
	 */
	public MyBPlusTreeNode getNode(Integer key) {
		MyBPlusTreeNode node = root;
		boolean found = false;

		/** 19가 삭제되었으면 bpTree는 leaf 노드가 된 상태 */
		// 리프 노드에서 키 찾음
		if (node.isLeaf()) {
			for (int i = 0; i < node.getKeyList().size(); i++) {
				if (key == node.getKeyList().get(i)) {
					found = true;
					break;
				}
			}
		}

		else {
			while (node.getKeyList().size() != 0 && node != null && !found) {
				// 현재 노드의 마지막 키 값보다 크거나 같은 경우를 먼저 처리
				if (key >= node.getKeyList().get(node.getKeyList().size() - 1)) {
					if (node.getChildren() != null && !node.isLeaf()) {
						System.out.println("larger than or equal to " + node.getKeyList().get(node.getKeyList().size() - 1));
						node = node.getChildren().get(node.getKeyList().size());
					}
				} else {
					// 현재 노드의 모든 키를 순회하며 탐색
					for (int i = 0; i < node.getKeyList().size(); i++) {
						if (key < node.getKeyList().get(i)) {
							System.out.println("less than " + node.getKeyList().get(i));
							node = node.getChildren().get(i);    // 현재 노드의 leaf child로 이동
							break;
						} else if (key == node.getKeyList().get(i)) {
							System.out.println("larger than or equal to " + node.getKeyList().get(i));
							node = node.getChildren().get(i + 1);        // 현재 노드의 right child로 이동
							break;
						}
					}
				}
				// 리프 노드에서 키 찾음
				if (node.isLeaf()) {
					for (int i = 0; i < node.getKeyList().size(); i++) {
						if (key == node.getKeyList().get(i)) {
							found = true;
							break;
						}
					}
					break;        // 키 없는 경우 found = false인 채로 종료
				}
			}

		}
		// 모든 노드를 순회한 후 결과 출력하고 node 반환
		if (found) {
			System.out.println(key + " found");
			return node;
		}
		else {		// 못 찾으면 null 반환
			System.out.println(key + " not found");
			return null;
		}
	}


	/**
	 * 과제 Assignment4를 위한 메소드:
	 *
	 * inorder traversal을 수행하여, 값을 오름차순으로 출력한다.
	 * 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21 이 순서대로
	 * add되었다고 했을 때,
	 * 1
	 * 2
	 * 3
	 * 4
	 * 5
	 * 6
	 * 7
	 * 8
	 * 9
	 * 10
	 * 11
	 * 12
	 * 13
	 * 14
	 * 15
	 * 16
	 * 17
	 * 18
	 * 19
	 * 20
	 * 21
	 * 위와 같이 출력되어야 함.
	 */

	// App.java에서 필요한 inorderTraverse() 함수는 매개변수가 없으므로
	// 함수 내부에서 inorderTraverse(MyBPlusTreeNode node)를 호출, 가장 먼저 root를 넘겨줌
	public void inorderTraverse() {
		inorderTraverse(root);
	}

	private void inorderTraverse(MyBPlusTreeNode node) {
		if (node == null) return;		// 순회할 노드가 없는 경우

		// leaf node가 아닌 경우
		// 내부 노드의 모든 자식 노드를 순서대로 방문
		if (!node.isLeaf()) {
			// 모든 자식을 순서대로 방문
			for (int i = 0; i < node.getChildren().size(); i++) {
				inorderTraverse(node.getChildren().get(i)); // 현재 노드의 자식들을 매개변수로 재귀 호출
			}
		} else { // 리프 노드인 경우 키들을 출력
			for (Integer key : node.getKeyList()) {
				System.out.println(key);
			}
		}
	}



	/**
	 * insert
	 * max key property 검사
	 */
	@Override
	public boolean add(Integer e) {
		MyBPlusTreeNode node = root;

		// root node이면서 leaf node인 경우
		if (node == root && node.isLeaf()) {
			node.setLeaf(true);
			if (leafList.isEmpty()) {
				leafList.add(node);
			}
		}

		// while문으로 leaf node까지 내려감
		while (node.getChildren() != null && !node.getChildren().isEmpty()) {
			List<Integer> keys = node.getKeyList();
			int flag = 0;

			for (int i=0; i< keys.size(); i++) {
				if (e < keys.get(i)) {
					node = node.getChildren().get(i);	// e가 key의 i번째 값보다 작은 경우 왼쪽 자식으로 이동
					flag = 1;
					break;
				}
			}

			// flag = 0이라는 건 node의 keylist에 있는 값들보다 크거나 같다는 의미이므로 가장 큰 위치인 리스트에 마지막에 위치시킴
			if (flag == 0) {
				node = node.getChildren().get(node.getChildren().size()-1);
			}
		}

		// 현재 e가 들어갈 node를 찾은 상태
		// 해당 node의 keyList의 어느 인덱스에 들어가야 되는지 찾음
		List<Integer> keys = node.getKeyList();
		int position = 0;
		while (position < keys.size() && e > keys.get(position)) {
			position++;
		}

		keys.add(position, e);		// 찾은 위치에 새로운 값 추가


		// 값을 추가 후 max key property에 어긋나는 경우
		if (node.getKeyList().size() > MAX_KEY) {
			bpTreeSplitChild(node);		// 삽입은 항상 leaf node에 되므로 일단 bpTreeSplit함수 호출
		}


//		System.out.println("[INSERT KEY] key = " + keys);
//		System.out.println("[ROOT의 KEY] key = " + root.getKeyList());

//		System.out.println("[NODE] key = " + node.getKeyList());
//		for (int i=0; i<leafList.size(); i++) {
//			System.out.print(leafList.get(i).getKeyList() + " ");
//		}
//		System.out.println();

		return true;
	}

	// b+tree에서 split은 leaf node인지 아닌지에 따라 다르게 작동함
	// leaf node인 경우 bpTreeSplitChild
	public void bpTreeSplitChild(MyBPlusTreeNode T) {
		int middleIdx = (int)Math.ceil((T.getKeyList().size()-1)/2.0);		// leaf node에서의 split이므로 올림
		Integer middleKey = T.getKeyList().get(middleIdx);

		// P 노드
		MyBPlusTreeNode parent = T.getParent();

		// rightNode == RightNode
		MyBPlusTreeNode rightNode = new MyBPlusTreeNode();
		rightNode.setKeyList(new ArrayList<>(T.getKeyList().subList(middleIdx, T.getKeyList().size())));		// right 노드
		T.getKeyList().subList(middleIdx, T.getKeyList().size()).clear();		// T -> left node로 만듦

		rightNode.setLeaf(true);
		T.setLeaf(true);

		// 새로운 루트 생성해야 하는 경우
		if (parent == null) {
			/** 새로운 루트 노드 생성하고 middle 저장 */
			MyBPlusTreeNode newRoot = new MyBPlusTreeNode();
			newRoot.setParent(null);
			newRoot.setLeaf(false);		// 새로 만들어진 root는 leaf node 아님

			// newRoot의 값들 초기화 후 root의 key에 middle 값 추가
			// newRoot에 왼쪽, 오른쪽 자식들 연결
			newRoot.setKeyList(new ArrayList<>());
			newRoot.getKeyList().add(middleKey);
			newRoot.setChildren(new ArrayList<>());
			newRoot.getChildren().add(T);
			newRoot.getChildren().add(rightNode);

			// leaf list에도 왼쪽, 오른쪽 노드 추가
			leafList.clear();		// root 노드이면서 leaf 노드인 경우에 leafList에 값을 추가해놨기 때문에 clear 하고 다시 추가
			leafList.add(T);
			leafList.add(rightNode);

			// 부모 노드 연결
			T.setParent(newRoot);
			rightNode.setParent(newRoot);
			this.root = newRoot;		// 현재 root 노드 업데이트
		}
		else {
			parent.getKeyList().add(parent.getKeyList().size(), middleKey);
			parent.getChildren().add(parent.getKeyList().size(), rightNode);

			rightNode.setParent(parent);
			parent.setLeaf(false);		// parent는 leaf node가 아니므로

			// parent 노드에 값이 추가됐으니까 max key 확인
			// leaf node 이외의 노드에서 max key property 조건을 어기는 경우
			if (parent.getKeyList().size() > MAX_KEY) {
				bTreeSplitChild(parent);		// 내부 노드인 경우 b-tree와 동일한 방식으로 split
			}

			leafList.add(rightNode);
		}
	}

	// leaf node 이외의 node split
	public void bTreeSplitChild(MyBPlusTreeNode T) {
		int middleIdx = (int)Math.floor((T.getKeyList().size() - 1) / 2.0);		// 내부 노드에서 split이므로 내림
		Integer middleKey = T.getKeyList().get(middleIdx);

		// b-tree에서의 split 방식과 동일
		MyBPlusTreeNode parent = T.getParent();
		MyBPlusTreeNode rightNode = new MyBPlusTreeNode();
		// T 노드의 key들에서 right에 들어갈 부분만큼 찢어서 새로운 리스트로 만든 후 rightNode의 keyList로 만듦
		rightNode.setKeyList(new ArrayList<>(T.getKeyList().subList(middleIdx + 1, T.getKeyList().size())));
		// 자식 노드도 동일하게 rightNode에 할당
		rightNode.setChildren(new ArrayList<>(T.getChildren().subList(middleIdx + 1, T.getChildren().size())));

		T.getKeyList().subList(middleIdx, T.getKeyList().size()).clear(); // T -> left node로 만듦
		// T는 leaf node로 만들었으므로 right node에 해당하는 부분을 subList로 만들고 삭제
		T.getChildren().subList(middleIdx + 1, T.getChildren().size()).clear();

		rightNode.setLeaf(false);
		T.setLeaf(false);

		// rightNode의 자식들의 부모를 rightNode로 설정
		for (MyBPlusTreeNode child : rightNode.getChildren()) {
			child.setParent(rightNode);
		}

		// 부모 노드가 없는 경우 새로운 root node 생성
		if (parent == null) {
			MyBPlusTreeNode newRoot = new MyBPlusTreeNode();
			newRoot.setParent(null);
			newRoot.setLeaf(false);
			newRoot.setKeyList(new ArrayList<>());
			newRoot.getKeyList().add(middleKey);
			newRoot.setChildren(new ArrayList<>());
			newRoot.getChildren().add(T);
			newRoot.getChildren().add(rightNode);

			T.setParent(newRoot);
			rightNode.setParent(newRoot);
			this.root = newRoot;
		} else {
			int parentInsertionIndex = parent.getChildren().indexOf(T);
			parent.getKeyList().add(parentInsertionIndex, middleKey);
			parent.getChildren().add(parentInsertionIndex + 1, rightNode);
			rightNode.setParent(parent);

			// parent에 값이 추가됐으므로 max key 확인하고 어기는 경우 다시 bTreeSplitChild() 호출
			if (parent.getKeyList().size() > MAX_KEY) {
				bTreeSplitChild(parent);
			}
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		/**
		 * (아이디어)
		 * leafList에 있는 node에서 keyList를 순회하며 ArrayList<Integer>로 선언한 tempList에 담고
		 * tempList의 iterator()를 반환함
		 */
		ArrayList<Integer> tempList = new ArrayList<>();

		for (int i=0; i < leafList.size(); i++) {
			Iterator<Integer> iterator = leafList.get(i).getKeyList().iterator();
			while (iterator.hasNext()) {
				tempList.add(iterator.next());
			}
		}
		return tempList.iterator();
	}

	/**
	 * findNode
	 * addNode에서 출력문 삭제한 후 코드 재활용
	 */
	public MyBPlusTreeNode findNode(Integer key) {
		MyBPlusTreeNode node = root;
		boolean found = false;

		/** 19가 삭제되었으면 bpTree는 leaf 노드가 된 상태 */
		// 리프 노드에서 키 찾음
		if (node.isLeaf()) {
			for (int i = 0; i < node.getKeyList().size(); i++) {
				if (key == node.getKeyList().get(i)) {
					found = true;
					break;
				}
			}
		}

		else {
			while (node.getKeyList().size() != 0 && node != null && !found) {
				// 현재 노드의 마지막 키 값보다 크거나 같은 경우를 먼저 처리
				if (key >= node.getKeyList().get(node.getKeyList().size() - 1)) {
					if (node.getChildren() != null && !node.isLeaf()) {
						node = node.getChildren().get(node.getKeyList().size());
					}
				} else {
					// 현재 노드의 모든 키를 순회하며 탐색
					for (int i = 0; i < node.getKeyList().size(); i++) {
						if (key < node.getKeyList().get(i)) {
							node = node.getChildren().get(i);    // 현재 노드의 leaf child로 이동
							break;
						} else if (key == node.getKeyList().get(i)) {
							node = node.getChildren().get(i + 1);        // 현재 노드의 right child로 이동
							break;
						}
					}
				}
				// 리프 노드에서 키 찾음
				if (node.isLeaf()) {
					for (int i = 0; i < node.getKeyList().size(); i++) {
						if (key == node.getKeyList().get(i)) {
							found = true;
							break;
						}
					}
					break;        // 키 없는 경우 found = false인 채로 종료
				}
			}

		}
		// 모든 노드를 순회한 후 결과 출력하고 node 반환
		if (found) {
			return node;
		}
		else {		// 못 찾으면 null 반환
			return null;
		}
	}

	public void borrowLSNode(MyBPlusTreeNode T, MyBPlusTreeNode LS) {
//		System.out.println("[borrow from LS]");
		// T가 리프 노드인지 아닌지에 따라 순서가 달라짐
		// 리프 노드인 경우 T의 바뀐 최솟값을 부모 노드에도 반영해야 함
		// 리프 노드가 아닌 경우 부모 노드에 기존의 LS의 최댓값을 넣고 LS에서는 그 값을 삭제
		int indexOfLS = T.getParent().getChildren().indexOf(LS);		// LS의 위치

		if (T.isLeaf()) {
			T.getKeyList().add(0, LS.getKeyList().get(LS.getKeyList().size()-1));	// T에 LV 값 추가

			int indexOfT = T.getParent().getChildren().indexOf(T);
			LS.getKeyList().remove(LS.getKeyList().size()-1);		// LV 지움
			T.getParent().getKeyList().set(indexOfT - 1, T.getKeyList().get(0));        // 부모 노드 업데이트
			// 리프 노드인 경우 -> LS 쪽 트리에서 최댓값을 찾아 T child에 추가
			// LS의 바로 다음 자식 노드가 마지막이 아닐 수도 있으므로 끝까지 순회 후 마지막 자식을 찾음
			if (LS.getChildren() != null && !LS.getChildren().isEmpty()) {
				MyBPlusTreeNode temp = LS;
				while (temp.getChildren() != null && !temp.getChildren().isEmpty()) {
					temp = temp.getChildren().get(temp.getChildren().size() - 1);
				}
				T.getChildren().add(temp);    // 찾은 자식 노드를 T의 마지막 자식 노드로 설정
				temp.getParent().getChildren().remove(temp);        // 오른쪽 서브 트리에서 지움
				temp.setParent(T);
			}
		}
		else {

			int indexOfT = T.getParent().getChildren().indexOf(T);
			T.getKeyList().add(0, T.getParent().getKeyList().get(indexOfLS));
			T.getParent().getKeyList().set(indexOfT-1, LS.getKeyList().get(LS.getKeyList().size()-1));
			// 내부 노드인 경우 -> LS의 가장 오른쪽 자식을 모두 T의 자식으로 옮김
			MyBPlusTreeNode temp = LS.getChildren().get(LS.getChildren().size() - 1);
			temp.getParent().getChildren().remove(temp);
			T.getChildren().add(0, temp);
			temp.setParent(T);

			LS.getKeyList().remove(LS.getKeyList().size() - 1);
		}

	}

	/**
	 * (아이디어)
	 * 1. remove 함수에서 값 삭제 후 borrowOrMerge()함수를 호출
	 * 2. borrowOrMerge 함수에서 조건(RS에서 빌릴 수 있는지, 아닌지..) 판단 후 알맞게 borrow, merge 함수 호출
	 * 3. merge 함수 수행 후 부모 노드가 min key property를 어기는 경우 borrowOrMerge 재귀 호출
	 *
	 * 이때 1부터 순차적으로 삭제하므로 왼쪽 형제 노드에서 빌려오는 경우는 제외함
	 */
	public void borrowRSNode(MyBPlusTreeNode T, MyBPlusTreeNode RS) {
//		System.out.println("[borrow from RS]");
		int indexOfRS = T.getParent().getChildren().indexOf(RS);


		// T가 리프 노드인지 아닌지에 따라 순서가 달라짐
		// 리프 노드인 경우 RS의 바뀐 최솟값을 부모 노드에도 반영해야 함
		// 리프 노드가 아닌 경우 부모 노드에 기존의 RS의 최솟값을 넣고 RS에서는 그 값을 삭제
		if (T.isLeaf()) {
			T.getKeyList().add(T.getParent().getChildren().get(indexOfRS).getKeyList().get(0));		// T에 RV 값 추가
			int indexOfT = T.getParent().getChildren().indexOf(T);
			RS.getKeyList().remove(0);
			T.getParent().getKeyList().set(indexOfT, RS.getKeyList().get(0));        // 부모 노드 업데이트
			// 리프 노드인 경우 -> RS 쪽 트리에서 최솟값을 찾아 T child에 추가
			// RS의 바로 다음 자식 노드가 마지막이 아닐 수도 있으므로 끝까지 순회 후 마지막 자식을 찾음
			if (RS.getChildren() != null)  {
				MyBPlusTreeNode temp = RS;
				while (temp.getChildren() != null) {
					temp = temp.getChildren().get(0);
				}
				T.getChildren().add(temp);	// 찾은 자식 노드를 T의 마지막 자식 노드로 설정
				temp.getParent().getChildren().remove(temp);		// 오른쪽 서브 트리에서 지움
				temp.setParent(T);
			}
		}
		else {
			T.getKeyList().add(T.getParent().getKeyList().get(indexOfRS-1));
			// 내부 노드인 경우 -> RS의 가장 왼쪽 자식을 모두 T의 자식으로 옮김
			MyBPlusTreeNode temp = RS.getChildren().get(0);
			T.getChildren().add(temp);
			temp.getParent().getChildren().remove(temp);
			temp.setParent(T);


			T.getParent().getKeyList().set(indexOfRS-1, RS.getKeyList().get(0));
			RS.getKeyList().remove(0);
		}
	}

	public void mergeLSNode(MyBPlusTreeNode T, MyBPlusTreeNode LS) {
//		System.out.println("LS merge node");
		int indexOfLS = T.getParent().getChildren().indexOf(LS);
		if (T.isLeaf()) {
			List<Integer> tempList = new ArrayList<>(T.getKeyList());
			T.getKeyList().clear();
			for (Integer e : LS.getKeyList()) {
				T.addKeyList(e);
			}

			for (Integer e: tempList) {
				T.addKeyList(e);
			}

			T.getParent().getChildren().remove(LS);
			T.getParent().getKeyList().remove(indexOfLS);		// check

			if (T.getParent().getKeyList().size() == 0) { // 부모 노드의 키가 없는 경우
				if (T.getParent() == this.root) { // 부모 노드가 루트 노드인 경우
					this.root = T; // T를 새로운 루트 노드로 설정
					/** 새로운 루트 노드가 리프 노드인지 확인 */
					if (T.getChildren() == null || T.getChildren().isEmpty()) {
						root.setLeaf(true);
					} else {
						T.setLeaf(false);
					}

//					System.out.println("new root = " + root.getKeyList());
					T.setParent(null); // T의 부모 노드를 null로 설정
				} else {
					borrowOrMerge(T.getParent());
				}
			}

			// 리프 노드에서도 삭제
			leafList.remove(LS);
		}

		else {        // borrowOrMerge()가 재귀적으로 호출되어 실행되는 코드
			// 트리 속의 데이터는 항상 정렬되어 있어야 하므로
			// LS + T의 부모노드의 키 + T 순서로 add

			List<Integer> tempList = new ArrayList<>(T.getKeyList());
			T.getKeyList().clear();
			for (Integer e : LS.getKeyList()) {
				T.addKeyList(e);
			}

			T.addKeyList(T.getParent().getKeyList().get(indexOfLS));

			for (Integer e: tempList) {
				T.addKeyList(e);
			}

			// T 노드에 LS 노드의 자식들을 추가
			int idx = 0;
			for (MyBPlusTreeNode child : LS.getChildren()) {
				child.setParent(T);        // 자식 노드의 부모를 T로 설정
				T.getChildren().add(idx++, child);        // 그 후 T의 자식 리스트에 추가
//				System.out.println("	child: " + child.getKeyList());
			}

			// 부모 노드에서 LS 노드와 관련된 키와 포인터를 제거
			T.getParent().getChildren().remove(LS);
			T.getParent().getKeyList().remove(indexOfLS);

			// 만약 부모 노드의 키가 모두 사라졌다면, T 노드를 새로운 루트로 설정
			if (T.getParent().getKeyList().size() == 0) { // 부모 노드의 키가 없는 경우
				if (T.getParent() == this.root) { // 부모 노드가 루트 노드인 경우
					this.root = T; // T를 새로운 루트 노드로 설정
					if (T.getChildren() == null || T.getChildren().isEmpty()) {
						T.setLeaf(true);
					} else {
						T.setLeaf(false);
					}
//					System.out.println("new root = " + root.getKeyList());
					T.setParent(null); // T의 부모 노드를 null로 설정
				} else {
					borrowOrMerge(T.getParent());
				}
			}
		}
	}

	// RS에서 값을 빌려올 수 없는 경우 머지
	public void mergeRSNode(MyBPlusTreeNode T, MyBPlusTreeNode RS) {
//		System.out.println("[RS merge node]");
		if (T.isLeaf()) {

			// T 노드에 RS 노드의 키를 추가
			for (Integer e: RS.getKeyList()) {
				T.addKeyList(e);
			}

			T.getParent().getChildren().remove(RS);		// T와 RS를 합쳤으므로 RS 노드 삭제
			T.getParent().getKeyList().remove(0);
			/**
			 * 1. T가 루트 노드인 경우
			 * 2. 부모 노드 == 내부 노드 && 키가 하나였던 걸 삭제한 경우
			 */
			if (T.getParent().getKeyList().size() == 0) { // 부모 노드의 키가 없는 경우
				if (T.getParent() == this.root) { // 부모 노드가 루트 노드인 경우
					this.root = T; // T를 새로운 루트 노드로 설정
					/** 새로운 루트 노드가 리프 노드인지 확인 */
					if (T.getChildren() == null || T.getChildren().isEmpty()) {
						root.setLeaf(true);
					} else {
						T.setLeaf(false);
					}

//					System.out.println("new root = " + root.getKeyList());
					T.setParent(null); // T의 부모 노드를 null로 설정
				} else {
					borrowOrMerge(T.getParent());
				}
			}

			// 리프 노드에서도 삭제
			leafList.remove(RS);
		}
		else {		// borrowOrMerge()가 재귀적으로 호출되어 실행되는 코드
			// 트리 속의 데이터는 항상 정렬되어 있어야 하므로
			// 부모 노드의 키 먼저 추가
			T.addKeyList(T.getParent().getKeyList().get(0));
			for (Integer e: RS.getKeyList()) {
				T.addKeyList(e);
			}
			// T 노드에 RS 노드의 자식들을 추가
			for (MyBPlusTreeNode child: RS.getChildren()) {
				child.setParent(T);		// 자식 노드의 부모를 T로 설정
				T.getChildren().add(child);		// 그 후 T의 자식 리스트에 추가
//				System.out.println("	child: " + child.getKeyList());
			}

			// 부모 노드에서 RS 노드와 관련된 키와 포인터를 제거
			T.getParent().getChildren().remove(RS);
			T.getParent().getKeyList().remove(0);

			// 만약 부모 노드의 키가 모두 사라졌다면, T 노드를 새로운 루트로 설정
			if (T.getParent().getKeyList().size() == 0) { // 부모 노드의 키가 없는 경우
				if (T.getParent() == this.root) { // 부모 노드가 루트 노드인 경우
					this.root = T; // T를 새로운 루트 노드로 설정
					/** 새로운 루트 노드가 리프 노드인지 확인 */
					if (T.getChildren() == null || T.getChildren().isEmpty()) {
						T.setLeaf(true);
					} else {
						T.setLeaf(false);
					}
//					System.out.println("new root = " + root.getKeyList());
					T.setParent(null); // T의 부모 노드를 null로 설정
				} else {
					borrowOrMerge(T.getParent());
				}
			}
		}
	}

	/**
	 * (아이디어)
	 * 키는 원하는 키 한 번만 삭제해야 하고(-> remove 함수에서) 삭제 후 위의 노드까지 타고 올라가야 하는 경우가 있으므로
	 * 따로 borrowOrMerge 함수를 만든 후 경우에 따라 재귀적으로 호출함
	 */
	public void borrowOrMerge(MyBPlusTreeNode T) {
		// 부모 노드가 존재하는 경우
		// -> remove()에서 확인하고 borrowOrMerge() 호출하므로 없어도 되는 부분
		if (T.getParent() != null) {
			// LS 노드 찾기
			MyBPlusTreeNode LS = null;
			int indexOfT = T.getParent().getChildren().indexOf(T);
			if (indexOfT > 0) {
				LS = T.getParent().getChildren().get(indexOfT - 1);
			}

			// RS 노드 찾기
			MyBPlusTreeNode RS = null;
			if (indexOfT < T.getParent().getChildren().size() - 1) {
				RS = T.getParent().getChildren().get(indexOfT + 1);
			}

			// T가 min key property를 어기는 경우
			if (T.getKeyList().size() < MIN_KEY) {
				// LS 노드에서 키를 빌림
				if (LS != null && LS.getKeyList().size() > MIN_KEY) {
					borrowLSNode(T, LS);
				}
				// LS 노드에서 빌릴 수 없는 경우 RS 노드에서 빌림
				else if (RS != null && RS.getKeyList().size() > MIN_KEY) {
					borrowRSNode(T, RS);
				}
				// LS, RS에서 빌릴 수 없는 경우 merge
				else {
					if (LS != null) {
						mergeLSNode(T, LS);
					} else {
						mergeRSNode(T, RS);
					}

					// 부모 노드의 키 개수가 min key property를 어기는 경우 재귀 호출
					if (T != root && T.getParent().getKeyList().size() < MIN_KEY) {
						borrowOrMerge(T.getParent());
					}
				}
			}
		}
	}

	/**
	 * 만약 가장 작은 키를 삭제한다면 내부 노드의 키들도 업데이트 해야 함
	 * @param T
	 * @param P
	 * @param minKey
	 */
	public void updateInternalNode(MyBPlusTreeNode T, MyBPlusTreeNode P, int minKey) {
		if (P == null) return;
		if (minKey < P.getKeyList().get(0)) updateInternalNode(T, P.getParent(), minKey);
		else if (minKey == P.getKeyList().get(0)) {
			// T의 부모 노드가 존재하고, 자식이 2개 이상인 경우
			int indexOfT = T.getParent().getChildren().indexOf(T);
			// -1인 경우 따로 처리
			if (T.getParent() != null && T.getParent().getChildren().size() > indexOfT+1 && T.getMinKey() == -1) {
				// T의 부모 노드의 두 번째 자식 노드의 최소 키 값을 P의 첫 번째 키 값으로 설정
				P.getKeyList().set(0, T.getParent().getChildren().get(indexOfT+1).getMinKey());
			} else {
				// T의 최소 키 값을 P의 첫 번째 키 값으로 설정
				P.getKeyList().set(0, T.getMinKey());
			}
		}
		else {
			int indexOfT = T.getParent().getChildren().indexOf(T);
			int targetIdx = P.getKeyList().indexOf(minKey);

			if (T.getParent() != null && T.getParent().getChildren().size() > indexOfT+1 && T.getMinKey() == -1) {
				// T의 부모 노드의 두 번째 자식 노드의 최소 키 값을 P의 첫 번째 키 값으로 설정
				P.getKeyList().set(targetIdx, T.getParent().getChildren().get(indexOfT+1).getMinKey());
			}
			else P.getKeyList().set(targetIdx, T.getMinKey());
		}
	}

	/**
	 * App.java에서 remove하고
	 * System.out.println("remove test: " + (bpTree.size() == 0));
	 * bpTree.size()의 값과 확인하는 방식으로 테스트 함
	 */
	@Override
	public boolean remove(Object o) {		// object는 Integer
		/**
		 * 1부터 마지막 번호의 노드까지 차례대로 remove하므로 왼쪽 노드에서 빌리는 경우는 제외하고 구현
		 * 삭제에 성공하면 true
		 * 삭제 실패하면 false 반환
		 */
		Integer key = (Integer) o;
		boolean flag = false;

		MyBPlusTreeNode T = findNode(key);
		if (T == null) return false;
		int indexOfKey = T.getKeyList().indexOf(key);


		// root 노드의 key를 삭제하는 부분
		if (T == root) {
			T.getKeyList().remove(indexOfKey);
			flag = true;
			/** b+tree에 모든 키가 삭제된 경우 root 노드 init */
			if (root.getKeyList().size() == 0) {
				root = new MyBPlusTreeNode();

				root.setParent(null); 					// root의 부모는 없으므로 null을 설정
				root.setKeyList(new ArrayList<>()); 	// 빈 키 리스트로 초기화
				root.setChildren(new ArrayList<>()); 	// 빈 자식 리스트로 초기화
				root.setLeaf(true);			// 생성된 순간은 root == leaf

				// leafList를 LinkedList의 새 인스턴스로 초기화
				leafList = new LinkedList<>();
			}
		}

		else  {
			boolean isMinKey = key == T.getMinKey();
			T.getKeyList().remove(indexOfKey);
			if (isMinKey) updateInternalNode(T, T.getParent(), key);		// 삭제하는 키가 노드의 최소 키라면 호출
			borrowOrMerge(T);
			flag = true;
		}

		// 현재 leaf list 상태 확인
//		for (int i=0; i<leafList.size(); i++) {
//			System.out.print(leafList.get(i).getKeyList() + " ");
//		}
//		System.out.println();


		return flag;
	}



	/**
	 * (문제 상황)
	 * size() 함수를 따로 구현하지 않아 App.java에서 size()함수의 반환 값이 항상 0이었음
	 *
	 * (아이디어)
	 * 구현해야할 size() 함수에는 매개변수가 없으므로
	 * 따로 countKeys(MyBPlusTreeNode node)를 만들어 내부에서 호출하는 방식으로 구현
	 *
	 * (구현 방식)
	 * root 노드에서부터 시작해서 모든 자식 노드를 재귀적으로 탐색하면서 키의 개수를 더함
	 */
	@Override
	public int size() {
		return countKeys(root);
	}

	// size() 함수에서만 호출하므로 private으로 선언
	private int countKeys(MyBPlusTreeNode node) {
		// 더이상 노드가 없는 경우 스톱
		if (node == null) { return 0; }

		int count = 0;
		if (node.isLeaf()) {
//			System.out.println("node의 keylist: " + node.getKeyList());
			count = node.getKeyList().size();
		} else {	// 내부 노드인 경우 해당 노드의 child만큼 재귀 호출함
			for (MyBPlusTreeNode child : node.getChildren()) {
				count += countKeys(child);
			}
		}
		return count;
	}

	@Override
	public Comparator<? super Integer> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer last() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Integer> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer lower(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer floor(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer ceiling(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer higher(Integer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer pollLast() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableSet<Integer> descendingSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Integer> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> subSet(Integer fromElement, boolean fromInclusive, Integer toElement,
										boolean toInclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> headSet(Integer toElement, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NavigableSet<Integer> tailSet(Integer fromElement, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> headSet(Integer toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<Integer> tailSet(Integer fromElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
