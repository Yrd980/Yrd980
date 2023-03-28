#include<iostream>
#include<stack>
using namespace std;
const int MAX = 1010;
int arr[MAX];
stack<int>s;

int main()
{
	int m, n,t;
	cin >> m >> n >> t;
	while (t--) {
		while (!s.empty()) {
			s.pop();
		}
		for (int i = 0; i <= n; i++) {
			cin >> arr[i];
		}
		int cur = 1;
		bool flag = true;
		for (int i = 1; i <= n; i++) {
			s.push(i);
			if (s.size() > m) {
				flag = false;
				break;
			}
			while (!s.empty() && s.top() == arr[cur]) {
				s.pop();
				cur++;
			}
		}
		if (s.empty() == true && flag == true) {
			cout << "yes" << endl;
		}
		else {
			cout << "no" << endl;
		}
	}

	return 0;
}