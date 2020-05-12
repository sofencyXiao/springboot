package com.sofency.elasticsearch_demo;

import com.alibaba.fastjson.JSONObject;
import com.sofency.elasticsearch_demo.pojo.User;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ElasticsearchDemoApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    //测试索引的创建
    @Test
    void contextLoads() throws IOException {
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("sophia");
        //客户端执行请求 请求后获得响应 下面相当于 PUT sophia
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.index());
    }
    //测试获取索引
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("sophia");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引的请求
    @Test
    void TestDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("sophia");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }


    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        User user = new User("sofency",11);
        IndexRequest indexRequest = new IndexRequest("sophia");
        //规则 put /sophia /_doc/1
        indexRequest.id("3");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.timeout("1s");

        //将我们的对象放入到请求中 json
        indexRequest.source(JSONObject.toJSONString(user),XContentType.JSON);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());//返回执行后的状态
    }

    //判断文档是否存在
    @Test
    void TestExistDocument() throws IOException {
        GetRequest request = new GetRequest("sophia");
        //不获取文档的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = client.exists(request,RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //获取文档的信息
    @Test
    void GetDocumentInfo() throws IOException {
        GetRequest request = new GetRequest("sophia","1");
        GetResponse documentFields = client.get(request, RequestOptions.DEFAULT);
        System.out.println("查询出来文档的索引信息"+documentFields.getIndex());
        System.out.println("查询出来文档的类型"+documentFields.getType());
        System.out.println("查询出来文档的ID"+documentFields.getId());
        System.out.println("查询出来文档的版本信息"+documentFields.getVersion());
        System.out.println("查询出来文档的序号"+documentFields.getSeqNo());
        System.out.println("查询出来文档的信息"+documentFields.getSource());
    }

    //更新文档信息
    @Test
    void UpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("sophia","1");
        //设置请求时间为1s
        request.timeout("1s");
        User user = new User("武汉",199);
        request.doc(JSONObject.toJSONString(user),XContentType.JSON);
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }
    //删除文档信息
    @Test
    void DeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("sophia","1");
        //设置请求时间为1s
        request.timeout("1s");
        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    //批量更新数据
    @Test
    void TestBulk() throws IOException {
        BulkRequest request = new BulkRequest("sophia");
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("sofency1",19));
        list.add(new User("sofency2",20));
        list.add(new User("sofency3",21));
        list.add(new User("sofency4",22));
        list.add(new User("sofency5",23));
        list.add(new User("sofency6",24));

        for(int i=0;i<list.size();i++){
            request.add(
                    new IndexRequest("sophia").id(""+(i+1))
                    .source(JSONObject.toJSONString(list.get(i)),XContentType.JSON));
        }
        //接收回应
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println("批量插入的结果"+bulk);
    }

    //查询
    //SearchRequest  搜索请求
    //SearchSourceBuilder 搜索条件构造
    //HighLightBuilder 高亮
    //TermQueryBuilder 精确查询
    //MatchAllQueryBuilder  匹配所有
    //
    @Test
    void TestSearch() throws IOException {
        SearchRequest search = new SearchRequest("sophia");
        //构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //精确搜索
        //QueryBuilders.matchAllQuery()  匹配所有
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","sofency1");
//        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();//匹配所有

        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        search.source(builder);

        SearchResponse search1 = client.search(search, RequestOptions.DEFAULT);
        System.out.println(search1.getHits().getHits());
        for (SearchHit hit : search1.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public void LinkedListDemo(){
//        LinkedList list =new LinkedList<>();
//        list.addFirst();

        List<Integer> list  = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(8);
        list.add(2);
        list.add(3);
        list.stream().sorted((o1,o2)->{
            return o1-o2;
        }).forEach(System.out::println);
    }

}
