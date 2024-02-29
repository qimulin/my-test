# 正则常用表达式释义

## 表达式记录
<table>
    <tr>
        <th>表达式</th>
        <th>作用</th>
        <th>示例</th>
    </tr>
    <tr>
        <td>.*？</td>
        <td>表示匹配任意字符到下一个符合条件的字符
            .*具有贪婪的性质，首先匹配到不能匹配为止，根据后面的正则表达式，会进行回溯。.
            *？则相反，一个匹配以后，就往下进行，所以不会进行回溯，具有最小匹配的性质。
            ？表示非贪婪模式，即为匹配最近字符 如果不加?就是贪婪模式a.*bc 可以匹配  abcbcbc
        </td>
        <td>正则表达式a.*?xxx，可以匹配abxxx、axxxxx、abbbbbxxx</td>
    </tr>
</table>