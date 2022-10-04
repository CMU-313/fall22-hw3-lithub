package com.sismics.docs.rest.resource;


import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sismics.docs.core.constant.AclType;
import com.sismics.docs.core.constant.ConfigType;
import com.sismics.docs.core.constant.Constants;
import com.sismics.docs.core.constant.PermType;
import com.sismics.docs.core.dao.*;
import com.sismics.docs.core.dao.criteria.DocumentCriteria;
import com.sismics.docs.core.dao.criteria.TagCriteria;
import com.sismics.docs.core.dao.dto.*;
import com.sismics.docs.core.event.DocumentCreatedAsyncEvent;
import com.sismics.docs.core.event.DocumentDeletedAsyncEvent;
import com.sismics.docs.core.event.DocumentUpdatedAsyncEvent;
import com.sismics.docs.core.event.FileDeletedAsyncEvent;
import com.sismics.docs.core.model.context.AppContext;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.model.jpa.Score;
import com.sismics.docs.core.model.jpa.File;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.*;
import com.sismics.docs.core.util.jpa.PaginatedList;
import com.sismics.docs.core.util.jpa.PaginatedLists;
import com.sismics.docs.core.util.jpa.SortCriteria;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.exception.ServerException;
import com.sismics.rest.util.AclUtil;
import com.sismics.rest.util.RestUtil;
import com.sismics.rest.util.ValidationUtil;
import com.sismics.util.EmailUtil;
import com.sismics.util.JsonUtil;
import com.sismics.util.context.ThreadLocalContext;
import com.sismics.util.mime.MimeType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.*;

/**
 * Document REST resources.
 * 
 * @author bgamard
 */
@Path("/documentscore")
public class DocumentScoreResource extends BaseResource {

    protected static final DateTimeParser YEAR_PARSER = DateTimeFormat.forPattern("yyyy").getParser();
    protected static final DateTimeParser MONTH_PARSER = DateTimeFormat.forPattern("yyyy-MM").getParser();
    protected static final DateTimeParser DAY_PARSER = DateTimeFormat.forPattern("yyyy-MM-dd").getParser();

    private static final DateTimeFormatter DAY_FORMATTER = new DateTimeFormatter(null, DAY_PARSER);
    private static final DateTimeFormatter MONTH_FORMATTER = new DateTimeFormatter(null, MONTH_PARSER);
    private static final DateTimeFormatter YEAR_FORMATTER = new DateTimeFormatter(null, YEAR_PARSER);

    private static final DateTimeParser[] DATE_PARSERS = new DateTimeParser[]{
            YEAR_PARSER,
            MONTH_PARSER,
            DAY_PARSER};
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder().append( null, DATE_PARSERS).toFormatter();

    @POST
    @Path("{documentId: [a-z0-9\\-]+}")
    
    public Response postScore(
            @PathParam("documentId") String documentId,
            @FormParam("score") String score) {
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Get the document
        DocumentDao documentDao = new DocumentDao();
        Document myDocument = documentDao.getById(documentId);


        //set document score
        //myDocument.setScore(score);
        //documentDao.update(myDocument, principal.getId());
        
        // create the row for this score in the database
        Score newScore = new Score();
        newScore.setScore(Integer.parseInt(score));
        newScore.setUserId(principal.getId());
        newScore.setDocumentId(documentId);
        ScoreDao scoreDao = new ScoreDao();
        scoreDao.create(newScore);

        // Always return OK
        JsonObjectBuilder response = Json.createObjectBuilder()
                .add("status", "ok");
        return Response.ok().entity(response.build()).build();
    }

}
