#include<iostream>
#include<queue>
using namespace std;
const int MAX = 100;

struct mouse {
	int weight;
	int r;
}mouse[MAX];

int main()
{
	int num, ng,order;
	cin >> num >> ng;
	for (int i = 0; i < num; i++) {
		cin >> mouse[i].weight;
	}
	queue<int>q;
	for (int i = 0; i < num; i++) {
		cin >> order;
		q.push(order);
	}
	int temp = num, group;
	while (q.size() != 1) {
		if (temp % ng == 0) group = temp / ng;
		else group = temp / ng + 1;
		for (int i = 0; i < group; i++) {
			int k = q.front();
			for (int j = 0; j < ng; j++) {
				if (i * ng + j >= temp) break;
				int front = q.front();
				if (mouse[front].weight > mouse[k].weight) {
					k = front;
				}
				mouse[front].r = group + 1;
				q.pop();
			}
			q.push(k);
		}
		temp = group;
	}
	mouse[q.front()].r = 1;

	return 0;
}