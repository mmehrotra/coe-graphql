package com.graphql.coe;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.coe.entities.AuthData;
import com.graphql.coe.entities.Link;
import com.graphql.coe.entities.SigninPayload;
import com.graphql.coe.entities.User;
import com.graphql.coe.entities.Vote;
import com.graphql.coe.repository.LinkRepository;
import com.graphql.coe.repository.UserRepository;
import com.graphql.coe.repository.VoteRepository;

import java.time.Instant;
import java.time.ZoneOffset;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

/**
 * Mutation root
 */
public class Mutation implements GraphQLRootResolver {
    
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public Link createLink(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Link newLink = new Link(url, description, context.getUser().getId());
        linkRepository.saveLink(newLink);
        return newLink;
    }
    
    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public SigninPayload signinUser(AuthData auth) {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
    
    public Vote createVote(String linkId, String userId) {
        return voteRepository.saveVote(new Vote(Instant.now().atZone(ZoneOffset.UTC), userId, linkId));
    }
}
