--
-- Name: document_info; Type: TABLE; Schema: demo;
--
DROP TABLE IF EXISTS demo.document_info;
CREATE TABLE demo.document_info
(
    document_id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    content TEXT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone NOT NULL
);

--
-- Name: document_permissions; Type: TABLE; Schema: demo;
--
DROP TABLE IF EXISTS demo.document_permission;
CREATE TABLE demo.document_permission
(
    document_id VARCHAR(50) NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    can_view BOOLEAN NOT NULL DEFAULT false,
    can_edit BOOLEAN NOT NULL DEFAULT false,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone NOT NULL,
    PRIMARY KEY (document_id, user_id)
);