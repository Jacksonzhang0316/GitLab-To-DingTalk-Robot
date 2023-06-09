package top.waterstone.gitlab.gitlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class PipelineEventDTO {

    @JsonProperty("object_kind")
    private String objectKind;
    @JsonProperty("object_attributes")
    private ObjectAttributes objectAttributes;
    @JsonProperty("merge_request")
    private MergeRequest mergeRequest;
    @JsonProperty("user")
    private User user;
    @JsonProperty("project")
    private Project project;
    @JsonProperty("commit")
    private Commit commit;
    @JsonProperty("source_pipeline")
    private SourcePipeline sourcePipeline;
    @JsonProperty("builds")
    private List<Build> builds;


    @Data
    @Builder
    public static class ObjectAttributes {
        private int id;
        private int iid;
        private String ref;
        private boolean tag;
        private String sha;
        private String beforeSha;
        private String source;
        private String status;
        private List<String> stages;
        private String createdAt;
        private String finishedAt;
        private int duration;
        private List<Variable> variables;

        @Data
        @Builder
        public static class Variable {
            private String key;
            private String value;
        }
    }

    @Data
    @Builder
    public static class MergeRequest {
        private int id;
        private int iid;
        private String title;
        private String sourceBranch;
        private int sourceProjectId;
        private String targetBranch;
        private int targetProjectId;
        private String state;
        private String mergeStatus;
        private String detailedMergeStatus;
        private String url;
    }

    @Data
    @Builder
    public static class User {
        private int id;
        private String name;
        private String username;
        private String avatarUrl;
        private String email;
    }

    @Data
    @Builder
    public static class Project {
        private int id;
        private String name;
        private String description;
        private String webUrl;
        private String avatarUrl;
        private String gitSshUrl;
        private String gitHttpUrl;
        private String namespace;
        private int visibilityLevel;
        private String pathWithNamespace;
        private String defaultBranch;
    }

    @Data
    @Builder
    public static class Commit {
        private String id;
        private String message;
        private String timestamp;
        private String url;
        private Author author;

        @Data
        @Builder
        public static class Author {
            private String name;
            private String email;
        }
    }

    @Data
    @Builder
    public static class SourcePipeline {
        private Project project;
        private int pipelineId;
        private int jobId;
    }

    @Data
    @Builder
    public static class Build {
        private int id;
        private String stage;
        private String name;
        private String status;
        private String createdAt;
        private String startedAt;
        private String finishedAt;
        private Double duration;
        private Double queuedDuration;
        private String failureReason;
        private String when;
        private boolean manual;
        private boolean allowFailure;
        private User user;
        private Runner runner;
        private ArtifactsFile artifactsFile;
        private Environment environment;

        @Data
        @Builder
        public static class Runner {
            private int id;
            private String description;
            private boolean active;
            private String runnerType;
            private boolean isShared;
            private List<String> tags;
        }

        @Data
        @Builder
        public static class ArtifactsFile {
            private String filename;
            private Double size;
        }

        @Data
        @Builder
        public static class Environment {
            private String name;
            private String action;
            private String state;
            private String url;
        }
    }
}

