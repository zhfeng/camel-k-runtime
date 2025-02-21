
rest {
    configuration {
        host = "my-host"
        port = "9192"
    }

    configuration("undertow") {
        host = "my-undertow-host"
        port = "9193"
    }

    path("/my/path") {
        get("/get") {
            consumes("application/json")
            produces("application/json")
            to("direct:get")
        }
    }

    post {
        path("/post")
        consumes("application/json")
        produces("application/json")
        to("direct:post")
    }
}



from("timer:tick")
    .process().message {
        m -> m.headers["MyHeader"] = "MyHeaderValue"
    }
    .to("log:info")
