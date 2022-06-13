package Practice.Daily.BackTracking;

import java.io.*;
import java.util.*;

/*
 *  1. posList의 길이 len부터 시작해서 좌표들의 조합을 구한다.
 *  2. 진우의 현재 좌표 jState.pos와 아직 방문하지 않은 좌표 nPos와의 거리가 jState.hp이하이면 탐색을 진행한다.
    3. len만큼의 좌표를 탐색했고, jState.pos에서 집의 좌표 hPos의 거리가 jState.hp이하라면 탐색을 완전히 종료한다.
    4. 만약 len만큼의 좌표를 탐색하지 못했다면, len의 크기를 1씩 줄여가며 탐색을 계속한다.
 */
public class G5_20208 {
    static int N, M, H, len;
    static Pos hPos;
    static State jState;
    static List<Pos> posList;
    static boolean[] visited;
    static boolean solved = false;

    static class Pos {
        int y, x;

        Pos (int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class State {
        Pos pos;
        int hp;

        State(Pos pos, int hp) {
            this.pos = pos;
            this.hp = hp;
        }
    }

    static int getDist(Pos src, Pos dst) {
        return Math.abs(src.y - dst.y) + Math.abs(src.x - dst.x);
    }

    static void dfs(int lv) {
        if (lv == len) {
            if (getDist(jState.pos, hPos) <= jState.hp)
                solved = true;
        }else {
            for (int idx = 0; idx < posList.size(); idx++) {
                if (solved) break;
                if (visited[idx]) continue;

                Pos cPos = jState.pos;
                Pos nPos = posList.get(idx);
                int dist = getDist(jState.pos, nPos);
                if (dist > jState.hp) continue;

                int diff = dist - H;
                visited[idx] = true;
                jState.hp -= diff;
                jState.pos = nPos;
                dfs(lv + 1);
                jState.pos = cPos;
                jState.hp += diff;
                visited[idx] = false;
            }
        }
    }

    static int solution() {
        len = posList.size();

        for (; len > -1; len--) {
            visited = new boolean[posList.size()];
            jState = new State(hPos, M);
            dfs(0);

            if (solved)
                break;
        }

        return len;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] tokens = br.readLine().split(" ");
        N = Integer.parseInt(tokens[0]); M = Integer.parseInt(tokens[1]); H = Integer.parseInt(tokens[2]);

        posList = new ArrayList<>();
        for (int r = 0; r < N; r++) {
            tokens = br.readLine().split(" ");
            for (int c = 0; c < N; c++) {
                int num = Integer.parseInt(tokens[c]);

                if (num == 1)
                    hPos = new Pos(r, c);
                else if (num == 2)
                    posList.add(new Pos(r, c));
            }
        }
        System.out.println(solution());
    }
}
