package com.bottos.botc.sdk.net.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Create by xionglh on 2019-10-09
 */
public class AccountInfoResponse implements Serializable {

    private String account_name;

    private String pubkey;

    private BigDecimal balance;

    private BigDecimal staked_balance;

    private BigDecimal staked_space_balance;

    private BigDecimal staked_time_balance;

    private BigDecimal unStaking_balance;

    private long unStaking_timestamp;

    private BigDecimal unClaimed_block_reward;
    private BigDecimal unClaimed_vote_reward;
    private String deploy_contract_list;

    // 获取投票中的节点列表，新增字段
    private String location; // 节点地理位置信息
    private String description; // 节点描述信息
    private String votes;
    private boolean active;
    private String transit_votes;

    private List<Authority> authority;
    private Resource resource;
    private Vote vote;
    private BigDecimal totalAsset;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public BigDecimal getTotalAsset() {
        totalAsset = balance.add(staked_balance).add(staked_space_balance).add(staked_time_balance).add(unStaking_balance).divide(new BigDecimal(10).pow(8));
        return totalAsset;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
        this.totalAsset = totalAsset;
    }

    public BigDecimal getBalance() {
        if (balance == null)
            return BigDecimal.ZERO;
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getStaked_balance() {
        if (staked_balance == null)
            return BigDecimal.ZERO;
        return staked_balance;
    }

    public void setStaked_balance(BigDecimal staked_balance) {
        this.staked_balance = staked_balance;
    }

    public BigDecimal getStaked_space_balance() {
        if (staked_space_balance == null)
            return BigDecimal.ZERO;
        return staked_space_balance;
    }

    public void setStaked_space_balance(BigDecimal staked_space_balance) {
        this.staked_space_balance = staked_space_balance;
    }

    public BigDecimal getStaked_time_balance() {
        if (staked_time_balance == null)
            return BigDecimal.ZERO;
        return staked_time_balance;
    }

    public void setStaked_time_balance(BigDecimal staked_time_balance) {
        this.staked_time_balance = staked_time_balance;
    }

    public BigDecimal getUnStaking_balance() {
        if (unStaking_balance == null)
            return BigDecimal.ZERO;
        return unStaking_balance;
    }

    public void setUnStaking_balance(BigDecimal unStaking_balance) {
        this.unStaking_balance = unStaking_balance;
    }

    public long getUnStaking_timestamp() {
        return unStaking_timestamp;
    }

    public void setUnStaking_timestamp(long unStaking_timestamp) {
        this.unStaking_timestamp = unStaking_timestamp;
    }

    public BigDecimal getUnClaimed_block_reward() {
        if (unClaimed_block_reward == null)
            return BigDecimal.ZERO;
        return unClaimed_block_reward;
    }

    public void setUnClaimed_block_reward(BigDecimal unClaimed_block_reward) {
        this.unClaimed_block_reward = unClaimed_block_reward;
    }

    public BigDecimal getUnClaimed_vote_reward() {
        if (unClaimed_vote_reward == null)
            return BigDecimal.ZERO;
        return unClaimed_vote_reward;
    }

    public void setUnClaimed_vote_reward(BigDecimal unClaimed_vote_reward) {
        this.unClaimed_vote_reward = unClaimed_vote_reward;
    }

    public String getDeploy_contract_list() {
        return deploy_contract_list;
    }

    public void setDeploy_contract_list(String deploy_contract_list) {
        this.deploy_contract_list = deploy_contract_list;
    }

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTransit_votes() {
        return transit_votes;
    }

    public void setTransit_votes(String transit_votes) {
        this.transit_votes = transit_votes;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public static class Authority implements Serializable {

        private String author_account;
        private String weight;
        private boolean is_approved;

        public boolean isIs_approved() {
            return is_approved;
        }

        public void setIs_approved(boolean is_approved) {
            this.is_approved = is_approved;
        }

        public String getAuthor_account() {
            return author_account;
        }

        public void setAuthor_account(String author_account) {
            this.author_account = author_account;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }

    public static class Resource implements Serializable {

        private BigDecimal free_available_space;
        private BigDecimal free_used_space;
        private BigDecimal stake_available_space;
        private BigDecimal stake_used_space;
        private BigDecimal free_available_time;
        private BigDecimal free_used_time;
        private BigDecimal stake_available_time;
        private BigDecimal stake_used_time;

        public BigDecimal getFree_available_space() {
            if (free_available_space == null)
                return BigDecimal.ZERO;
            return free_available_space;
        }

        public void setFree_available_space(BigDecimal free_available_space) {
            this.free_available_space = free_available_space;
        }

        public BigDecimal getFree_used_space() {
            if (free_used_space == null)
                return BigDecimal.ZERO;
            return free_used_space;
        }

        public void setFree_used_space(BigDecimal free_used_space) {
            this.free_used_space = free_used_space;
        }

        public BigDecimal getStake_available_space() {
            if (stake_available_space == null)
                return BigDecimal.ZERO;
            return stake_available_space;
        }

        public void setStake_available_space(BigDecimal stake_available_space) {
            this.stake_available_space = stake_available_space;
        }

        public BigDecimal getStake_used_space() {
            if (stake_used_space == null)
                return BigDecimal.ZERO;
            return stake_used_space;
        }

        public void setStake_used_space(BigDecimal stake_used_space) {
            this.stake_used_space = stake_used_space;
        }

        public BigDecimal getFree_available_time() {
            if (free_available_time == null)
                return BigDecimal.ZERO;
            return free_available_time;
        }

        public void setFree_available_time(BigDecimal free_available_time) {
            this.free_available_time = free_available_time;
        }

        public BigDecimal getFree_used_time() {
            if (free_used_time == null)
                return BigDecimal.ZERO;
            return free_used_time;
        }

        public void setFree_used_time(BigDecimal free_used_time) {
            this.free_used_time = free_used_time;
        }

        public BigDecimal getStake_available_time() {
            if (stake_available_time == null)
                return BigDecimal.ZERO;
            return stake_available_time;
        }

        public void setStake_available_time(BigDecimal stake_available_time) {
            this.stake_available_time = stake_available_time;
        }

        public BigDecimal getStake_used_time() {
            if (stake_used_time == null)
                return BigDecimal.ZERO;
            return stake_used_time;
        }

        public void setStake_used_time(BigDecimal stake_used_time) {
            this.stake_used_time = stake_used_time;
        }
    }

    public static class Vote implements Serializable {

        private String delegate;

        private BigDecimal votes;

        public String getDelegate() {
            return delegate;
        }

        public void setDelegate(String delegate) {
            this.delegate = delegate;
        }

        public BigDecimal getVotes() {
            if (votes == null)
                return BigDecimal.ZERO;
            return votes;
        }

        public void setVotes(BigDecimal votes) {
            this.votes = votes;
        }
    }

}
