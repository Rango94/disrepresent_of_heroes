# disrepresent_of_heroes
一种DOTA2英雄的分布式表征<br>
a distributed representation method of DOTA2 hero.<br>
多次训练的结果表明这是一种可靠的表征方式<br>
The results of multiple trainings shows that this is a reliable method of representation.<br>
训练得到的英雄向量可以用来做英雄的聚类和比赛的胜负预测<br>
The hero vector trained by this method can be used to cluster analysis or outcome prediction.<br>
训练方法借鉴了word2vec所使用的训练方法，并且在其基础上进行了改进。<br>
具体改进在于建立了两个huffman树分别对应英雄之间的克制关系和协作关系，这两个huffman的非叶子节点的参数向量独立,但英雄向量共享。<br>
协同训练这两个huffman树使得英雄向量在这两个huffman树上的表现最优。<br>
训练语料库是按照统计概率自动生成的<br>
对于一个训练样例{a1,a2,x,b2,b1},根据a1,a2,b2,b1预测x。<br>
若在协作huffman树中，则有a1,a2对x有增益作用，并且a2增益大于a1增益；b2,b1对x有削减作用，并且b2的削减大于b1的削减。<br>
克制huffman树中同理。
