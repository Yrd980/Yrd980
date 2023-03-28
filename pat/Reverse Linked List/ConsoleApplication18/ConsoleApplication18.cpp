#include<iostream>
#include<algorithm>
using namespace std;
const int maxn = 10010;
struct node {
	int address, data, next;
	int order;
}nodes[maxn];
bool cmp(node a, node b) {
	return a.order < b.order;
}

int main()
{
	for (int i = 0; i < maxn; i++) {
		nodes[i].order = maxn;
	}
	int begin, n, k, address;
	cin >> begin >> n >> k;
	for (int i = 0; i < n; i++) {
		cin >> address;
		cin >> nodes[address].data >> nodes[address].next;
		nodes[address].address = address;
	}
	int p = begin, count = 0;
	while (p != -1) {
		nodes[p].order = count++;
		p = nodes[p].next;
	}
	sort(nodes, nodes + n, cmp);
	n = count;
	for (int i = 0; i < n / k; i++) {
		for (int j = (i + 1) * k - 1; j > i * k; j--) {
			cout << nodes[j].address << "  " << nodes[j].data << "  " << nodes[j - 1].address;
		}
		cout << nodes[i * k].address << " " << nodes[i * k].data << endl;
		if (i < n / k - 1) {
			cout << nodes[(i + 2) * k - 1].address;
		}
		else {
			if (n % k == 0) {
				cout << "-1" << endl;
			}
			else
			{
				cout << nodes[(i + 1) * k].address << endl;
				for (int i =n/ k * k; i < n; i++) {
					cout << nodes[i].address << " " << nodes[i].data;
					if (i < n - 1) {
						cout << nodes[i + 1].address;
					}
					else
					{
						cout << "-1" << endl;
					}
				}
			}
		
			
		}
	}

	return 0;
}