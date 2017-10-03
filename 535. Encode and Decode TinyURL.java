public class Codec {

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        return longUrl;

    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return shortUrl;

    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));



//WA



//Summary
Approach #1 Using Simple Counter[Accepted]

In order to encode the URL, we make use of a counter(ii), which is incremented for every new URL encountered. We put the URL along with its encoded count(ii) in a HashMap. This way we can retrieve it later at the time of decoding easily.

Java

public class Codec {
    Map<Integer, String> map = new HashMap<>();
    int i = 0;

    public String encode(String longUrl) {
        map.put(i, longUrl);
        return "http://tinyurl.com/" + i++;
    }

    public String decode(String shortUrl) {
        return map.get(Integer.parseInt(shortUrl.replace("http://tinyurl.com/", "")));
    }
}
Performance Analysis

The range of URLs that can be decoded is limited by the range of \text{int}int.

If excessively large number of URLs have to be encoded, after the range of \text{int}int is exceeded, integer overflow could lead to overwriting the previous URLs' encodings, leading to the performance degradation.

The length of the URL isn't necessarily shorter than the incoming \text{longURL}longURL. It is only dependent on the relative order in which the URLs are encoded.

One problem with this method is that it is very easy to predict the next code generated, since the pattern can be detected by generating a few encoded URLs.

Approach #2 Variable-length Encoding[Accepted]

Algorithm

In this case, we make use of variable length encoding to encode the given URLs. For every \text{longURL}longURL, we choose a variable codelength for the input URL, which can be any length between 0 and 61. Further, instead of using only numbers as the Base System for encoding the URLSs, we make use of a set of integers and alphabets to be used for encoding.

Java

public class Codec {

    String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    HashMap<String, String> map = new HashMap<>();
    int count = 1;

    public String getString() {
        int c = count;
        StringBuilder sb = new StringBuilder();
        while (c > 0) {
            c--;
            sb.append(chars.charAt(c % 62));
            c /= 62;
        }
        return sb.toString();
    }

    public String encode(String longUrl) {
        String key = getString();
        map.put(key, longUrl);
        return "http://tinyurl.com/" + key;
        count++;
    }

    public String decode(String shortUrl) {
        return map.get(shortUrl.replace("http://tinyurl.com/", ""));
    }
}
Performance Analysis

The number of URLs that can be encoded is, again, dependent on the range of \text{int}int, since, the same countcount will be generated after overflow of integers.

The length of the encoded URLs isn't necessarily short, but is to some extent dependent on the order in which the incoming \text{longURL}longURL's are encountered. For example, the codes generated will have the lengths in the following order: 1(62 times), 2(62 times) and so on.

The performance is quite good, since the same code will be repeated only after the integer overflow limit, which is quite large.

In this case also, the next code generated could be predicted by the use of some calculations.

Approach #3 Using hashcode[Accepted]

Algorithm

In this method, we make use of an inbuilt function \text{hashCode()}hashCode() to determine a code for mapping every URL. Again, the mapping is stored in a HashMap for decoding.

The hash code for a String object is computed(using int arithmetic) as −

s[0]*31^{(n - 1)} + s[1]*31^{(n - 2)} + ... + s[n - 1]s[0]∗31
​(n−1)
​​ +s[1]∗31
​(n−2)
​​ +...+s[n−1] , where s[i] is the ith character of the string, n is the length of the string.

Java

public class Codec {
    Map<Integer, String> map = new HashMap<>();

    public String encode(String longUrl) {
        map.put(longUrl.hashCode(), longUrl);
        return "http://tinyurl.com/" + longUrl.hashCode();
    }

    public String decode(String shortUrl) {
        return map.get(Integer.parseInt(shortUrl.replace("http://tinyurl.com/", "")));
    }
}
Performance Analysis

The number of URLs that can be encoded is limited by the range of \text{int}int, since \text{hashCode}hashCode uses integer calculations.

The average length of the encoded URL isn't directly related to the incoming \text{longURL}longURL length.

The \text{hashCode()}hashCode() doesn't generate unique codes for different string. This property of getting the same code for two different inputs is called collision. Thus, as the number of encoded URLs increases, the probability of collisions increases, which leads to failure.

The following figure demonstrates the mapping of different objects to the same hashcode and the increasing probability of collisions with increasing number of objects.

Encode_and_Decode_URLs

Thus, it isnt necessary that the collisions start occuring only after a certain number of strings have been encoded, but they could occur way before the limit is even near to the \text{int}int. This is similar to birthday paradox i.e. the probability of two people having the same birthday is nearly 50% if we consider only 23 people and 99.9% with just 70 people.

Predicting the encoded URL isnt easy in this scheme.

Approach #4 Using random number[Accepted]

Algorithm

In this case, we generate a random integer to be used as the code. In case the generated code happens to be already mapped to some previous \text{longURL}longURL, we generate a new random integer to be used as the code. The data is again stored in a HashMap to help in the decoding process.

Java

public class Codec {
    Map<Integer, String> map = new HashMap<>();
    Random r = new Random();
    int key = r.nextInt(Integer.MAX_VALUE);

    public String encode(String longUrl) {
        while (map.containsKey(key)) {
            key = r.nextInt(Integer.MAX_VALUE);
        }
        map.put(key, longUrl);
        return "http://tinyurl.com/" + key;
    }

    public String decode(String shortUrl) {
        return map.get(Integer.parseInt(shortUrl.replace("http://tinyurl.com/", "")));
    }
}
Performance Analysis

The number of URLs that can be encoded is limited by the range of \text{int}int.

The average length of the codes generated is independent of the \text{longURL}longURL's length, since a random integer is used.

The length of the URL isn't necessarily shorter than the incoming \text{longURL}longURL. It is only dependent on the relative order in which the URLs are encoded.

Since a random number is used for coding, again, as in the previous case, the number of collisions could increase with the increasing number of input strings, leading to performance degradation.

Determining the encoded URL isnt possible in this scheme, since we make use of random numbers.

Approach #5 Random fixed-length encoding[Accepted]

Algorithm

In this case, again, we make use of the set of numbers and alphabets to generate the coding for the given URLs, similar to Approach 2. But in this case, the length of the code is fixed to 6 only. Further, random characters from the string to form the characters of the code. In case, the code generated collides with some previously generated code, we form a new random code.

Java

public class Codec {
    String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    HashMap<String, String> map = new HashMap<>();
    Random rand = new Random();
    String key = getRand();

    public String getRand() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(alphabet.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }

    public String encode(String longUrl) {
        while (map.containsKey(key)) {
            key = getRand();
        }
        map.put(key, longUrl);
        return "http://tinyurl.com/" + key;
    }

    public String decode(String shortUrl) {
        return map.get(shortUrl.replace("http://tinyurl.com/", ""));
    }
}
Performance Analysis

The number of URLs that can be encoded is quite large in this case, nearly of the order (10+26*2)^6(10+26∗2)
​6
​​ .

The length of the encoded URLs is fixed to 6 units, which is a significant reduction for very large URLs.

The performance of this scheme is quite good, due to a very less probability of repeated same codes generated.

We can increase the number of encodings possible as well, by increasing the length of the encoded strings. Thus, there exists a tradeoff between the length of the code and the number of encodings possible.

Predicting the encoding isnt possible in this scheme since random numbers are used.

