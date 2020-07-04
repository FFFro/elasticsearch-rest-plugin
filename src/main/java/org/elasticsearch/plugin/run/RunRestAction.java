package org.elasticsearch.plugin.run;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.Table;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.rest.action.cat.AbstractCatAction;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;

/**
 * @author : FFFro
 * @date : 2020-07-04 11:58
 **/
public class RunRestAction extends AbstractCatAction {


    public RunRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_test/get", this);
        controller.registerHandler(POST, "/_test/post", this);
    }

    @Override
    protected RestChannelConsumer doCatRequest(RestRequest request, NodeClient client) {
        if (request.path().endsWith("get")) {
            return channel -> {
                try {
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("getRequest", "You know, for elasticsearch rest plugin").endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (final Exception e) {
                    channel.sendResponse(new BytesRestResponse(channel, e));
                }
            };
        } else {
            return channel -> {
                try {
                    XContentBuilder builder = channel.newBuilder();
                    builder.startObject().field("otherRequest", "You know, for elasticsearch rest plugin").endObject();
                    channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
                } catch (final Exception e) {
                    channel.sendResponse(new BytesRestResponse(channel, e));
                }
            };
        }
    }

    @Override
    protected void documentation(StringBuilder sb) {
        sb.append("/_test/example\n");
    }

    @Override
    protected Table getTableWithHeader(RestRequest request) {
        final Table table = new Table();
        table.startHeaders();
        table.addCell("test", "desc:test");
        table.endHeaders();
        return table;
    }

    @Override
    public String getName() {
        return "rest_handler_test";
    }

}
