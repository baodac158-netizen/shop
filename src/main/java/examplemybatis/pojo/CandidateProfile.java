package examplemybatis.pojo;

public record CandidateProfile(
        String name,
        int age,
        String techStack,
        String city,
        double expectedSalary
) {}
