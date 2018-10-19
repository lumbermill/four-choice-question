# Kotlinの基本

Kotlinでは以下のように「//」で始まる行や、「/* \*/」で囲まれた内部はコメントとされ、
コンパイルの時に無視されます。人間に向けた言葉を書くことが可能です（C言語と同じ）。

```
// コメント
/*
  これもコメント
 */
```

変数には大きく2種類あります。varは値を書き換え可能ですが、valは一度代入すると変更ができません。

```
var a = 1
a = 2
print(a)
val b = 1
// b = 2 error: val cannot be reassigned
print(b)
```

変数には数字だけでなく、文字列やその他たくさんの種類があります。

```
var c = "hello"
// c = 1 error: the integer literal does not conform to the expected type String
print(c)
```

javaClassを付けると、その変数の型を知ることができます。

```
var d = 0.5
print(a.javaClass) // int
print(c.javaClass) // String
print(d.javaClass) // double
```

違う型の代入や演算は出来ませんが、暗黙的に可能な場合もあります。
文字列の中に「$変数名」と書くと、その変数の値が代入されます。

```
var e = a + d
print(e) // 2.5
print("$a + $d = $e") // 2 + 0.5 = 2.5
```

条件分岐は if を使います。

```
if (a < 3) {
    print("a is less than 3.") // a is less than 3.
}
```

回数の決まった繰り返しには for が便利です。

```
for (i in 1..10) {
    print("$i") // 0, 1, .. 10
}
```

ある条件が成立する間、という形の繰り返しには while を使います。

```
var i = 0
while (i < 10) {
    print("$i") // 0, 1, .. 9
    i += 1
}
```

関数を定義する場合は、 fun という予約語を使います。

```
fun hello(someone: String){
    print("Hello, $someone.")
}

hello("John") // Hello, John.
```

配列とは、ある値が連続して格納されたデータ型のことです。
Kotlinでは、arrayListOfという関数を使って定義することができます。
配列の中に含まれる値のことを要素と呼びます。ある要素を指定するには
0番から始まる番号(インデックス)を指定します。存在しないインデックスを指定するとエラーになります。

```
var animals = arrayListOf("hedgehog","dog","turkey")
print(animals) // [hedgehog, dog, turkey]
print(animals[1]) // dog
// print(animals[3]) IndexOutOfBoundsException
```

配列に値を追加します。

```
animals.add("cow")
print(animals) // [hedgehog, dog, turkey, cow]
```

別の配列を指定してまとめて追加も可能です。

```
var two = arrayListOf("gorilla", "panda")
animals.addAll(two)
print(animals) // [hedgehog, dog, turkey, cow, gorilla, panda]
```

インデックスを指定して要素を上書きすることもできます。

```
animals[2] = "bird"
print(animals) // [hedgehog, dog, bird, cow, gorilla, panda]
```

中身を指定して削除します。

```
animals.remove("dog")
print(animals) // [hedgehog, bird, cow, gorilla, panda]
```

配列のインデックスを指定して削除します。

```
animals.removeAt(1)
print(animals) // [hedgehog, cow, gorilla, panda]
```

ハッシュとは、キーと値のペアを複数持ったデータ型です。
Kotlinでは、hashMapOfという関数を使って定義することができます。
キーを指定することで、その値を参照することができます。
存在しないキーを指定すると、nullが返されます。

```
var points = hashMapOf("taro" to 3, "jiro" to 5)
print(points) // {taro=3, jiro=5}
print(points["taro"]) // 3
print(points["saburo"]) // null
```

値を追加します。

```
points["siro"] = 7
print(points) // {taro=3, jiro=5, siro=7}
```

値を上書きします。

```
points["jiro"] = 4
print(points) // {taro=3, jiro=4, siro=7}
```

キーを指定して削除します。

```
points.remove("jiro")
print(points) // {taro=3, siro=7}
```
