package org.dfpl.lecture.database.assignment2.assignment2_22011819;

import java.util.List;

@SuppressWarnings("unused")
public class MyBPlusTreeNode {

	// Data Abstraction은 예시일 뿐 자유롭게 B+ Tree의 범주 안에서 어느정도 수정가능
	private MyBPlusTreeNode parent;
	private List<Integer> keyList;
	private List<MyBPlusTreeNode> children;
	private boolean isLeaf;			// 해당 노드가 leaf node인지 확인하기 위해 사용

	// 각 변수의 getter, setter
	public MyBPlusTreeNode getParent() {
		return parent;
	}

	public void setParent(MyBPlusTreeNode parent) {
		this.parent = parent;
	}

	public List<Integer> getKeyList() {
		return keyList;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setKeyList(List<Integer> keyList) {
		this.keyList = keyList;
	}

	public List<MyBPlusTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<MyBPlusTreeNode> children) {
		this.children = children;
	}

	public void addKeyList(Integer e) {
		this.keyList.add(e);
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}

	public int getMinKey() {
		if (keyList.size() == 0) return -1;		// 메서드 내부에서 return 받은 값이 -1이면 따로 처리해야됨

		if (isLeaf()) {
			return keyList.get(0);
		} else {
			return children.get(0).getMinKey();
		}
	}

	public void setMinKey(int newMinKey) {
		if (isLeaf()) {
			keyList.set(0, newMinKey);
		} else {
			children.get(0).setMinKey(newMinKey);
		}
	}
}
